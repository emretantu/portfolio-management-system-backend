package com.pmsystemtest.microservices.jwtservice.auth;

import com.pmsystemtest.microservices.jwtservice.config.JwtService;
import com.pmsystemtest.microservices.jwtservice.entity.Role;
import com.pmsystemtest.microservices.jwtservice.entity.Token;
import com.pmsystemtest.microservices.jwtservice.entity.TokenType;
import com.pmsystemtest.microservices.jwtservice.entity.User;
import com.pmsystemtest.microservices.jwtservice.exception.DuplicateEmailException;
import com.pmsystemtest.microservices.jwtservice.exception.MissingTokenException;
import com.pmsystemtest.microservices.jwtservice.exception.UserNotFoundException;
import com.pmsystemtest.microservices.jwtservice.repository.TokenRepository;
import com.pmsystemtest.microservices.jwtservice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> userOptional = repository.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
            throw new DuplicateEmailException();
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }





    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        var token = tokenRepository.findTokenByUserId(user.getId()).orElse(null);
        if(token != null){
            token.setExpiration(jwtService.extractExpiration(jwtToken).getTime());
            tokenRepository.save(token);
        }
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validate(String token){
        String username = jwtService.extractUsername(token);
        var user = repository.findByEmail(username)
                .orElseThrow();

        var dbToken = tokenRepository.findTokenByUserId(user.getId()).orElse(null);
        if(dbToken != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return jwtService.isTokenValid(token, userDetails) && jwtService.extractExpiration(token).getTime() == dbToken.getExpiration();
        }
        return false;
    }

    public Long findUserByToken(String token){
        String email = jwtService.extractUsername(token);
        var user = repository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getId();
    }


    public boolean validateWithKey(String token) {
        return jwtService.isTokenValidWithKey(token);
    }

    private void saveUserToken(User user, String jwtToken) {
        long expiration = jwtService.extractExpiration(jwtToken).getTime();
        var token = Token.builder()
                .user(user)
                .expiration(expiration)
                .build();
        var token1 = tokenRepository.findTokenByUserId(user.getId()).orElse(null);
        if(token1 == null){
            tokenRepository.save(token);
        }
    }

    public AuthenticationResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new MissingTokenException();
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail != null){
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if(jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                saveUserToken(user, accessToken);
                var token = tokenRepository.findTokenByUserId(user.getId()).orElse(null);
                if(token != null){
                    token.setExpiration(jwtService.extractExpiration(accessToken).getTime());
                    tokenRepository.save(token);
                }
                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }

        }
        return null;
    }
}

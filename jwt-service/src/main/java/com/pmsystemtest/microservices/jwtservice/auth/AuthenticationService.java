package com.pmsystemtest.microservices.jwtservice.auth;

import com.pmsystemtest.microservices.jwtservice.config.JwtService;
import com.pmsystemtest.microservices.jwtservice.entity.Role;
import com.pmsystemtest.microservices.jwtservice.entity.Token;
import com.pmsystemtest.microservices.jwtservice.entity.TokenType;
import com.pmsystemtest.microservices.jwtservice.entity.User;
import com.pmsystemtest.microservices.jwtservice.exception.DuplicateEmailException;
import com.pmsystemtest.microservices.jwtservice.repository.TokenRepository;
import com.pmsystemtest.microservices.jwtservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);

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
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean validate(String token){
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        var existToken = tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);

        return jwtService.isTokenValid(token, userDetails) && existToken;
    }


    public boolean validateWithKey(String token) {
        return jwtService.isTokenValidWithKey(token);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}

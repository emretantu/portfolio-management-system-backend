package com.pmsystemtest.microservices.jwtservice.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ){
        List<String> validationErrors = request.getValidationErrors();
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        }
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> register(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return ResponseEntity.ok(service.refreshToken(request, response));
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponse> validate(@RequestBody ValidateRequest request){

        // isValidWithKey
        if(!service.validateWithKey(request.getToken())){
            throw new RuntimeException("invalid jwt token");
        }

        ValidateResponse validateResponse = ValidateResponse.builder()
                .status(service.validate(request.getToken()))
                .build();
        return new ResponseEntity<>(validateResponse, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<UserIdResponse> findUserIdByToken(@RequestBody TokenRequest request){
        UserIdResponse userIdResponse = UserIdResponse.builder()
                .userId(service.findUserByToken(request.getToken()))
                .build();
        return ResponseEntity.ok(userIdResponse);
    }
}

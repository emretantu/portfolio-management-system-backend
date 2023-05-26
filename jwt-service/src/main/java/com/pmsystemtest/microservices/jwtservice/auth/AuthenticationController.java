package com.pmsystemtest.microservices.jwtservice.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
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
}

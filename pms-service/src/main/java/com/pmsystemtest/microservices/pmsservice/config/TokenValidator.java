package com.pmsystemtest.microservices.pmsservice.config;

import com.pmsystemtest.microservices.pmsservice.proxy.UserProxy;
import com.pmsystemtest.microservices.pmsservice.request.TokenRequest;
import com.pmsystemtest.microservices.pmsservice.request.UserIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final UserProxy userProxy;

    public boolean checkTokenByUserId(Long userId, String token){

        TokenRequest tokenRequest = TokenRequest.builder()
                .token(token)
                .build();

        UserIdResponse userIdResponse = userProxy.findUserIdByToken(tokenRequest).getBody();

        return userId == userIdResponse.getUserId();

    }
}

package com.pmsystemtest.microservices.pmsservice.proxy;

import com.pmsystemtest.microservices.pmsservice.request.TokenRequest;
import com.pmsystemtest.microservices.pmsservice.request.UserIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "jwt-service")
@Component
public interface UserProxy {

    @GetMapping("api/v1/users/{userId}")
    boolean isExistUser(@PathVariable Long userId);

    @PostMapping("api/v1/auth/token")
    ResponseEntity<UserIdResponse> findUserIdByToken(@RequestBody TokenRequest request);
}

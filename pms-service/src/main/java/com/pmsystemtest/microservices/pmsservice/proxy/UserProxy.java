package com.pmsystemtest.microservices.pmsservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jwt-service")
@Component
public interface UserProxy {

    @GetMapping("api/v1/users/{userId}")
    boolean isExistUser(@PathVariable Long userId);
}

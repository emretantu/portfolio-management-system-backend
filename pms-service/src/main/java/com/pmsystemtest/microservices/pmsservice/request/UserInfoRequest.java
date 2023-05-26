package com.pmsystemtest.microservices.pmsservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRequest {

    private Long userId;

    private String firstname;

    private String lastname;

    private Integer birthYear;
}

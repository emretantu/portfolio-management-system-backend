package com.pmsystemtest.microservices.pmsservice.exceptions.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse {

    private int status;

    private String exceptionType;

    private String message;

    private Timestamp timestamp;
}

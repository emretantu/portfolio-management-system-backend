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
public class SuccessResponse {
    private int statusCode;
    private String message;
    private Timestamp timestamp;
}

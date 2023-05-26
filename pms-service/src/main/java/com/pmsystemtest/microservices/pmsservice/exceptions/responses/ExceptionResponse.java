package com.pmsystemtest.microservices.pmsservice.exceptions.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;
    private List<String> errors;
}

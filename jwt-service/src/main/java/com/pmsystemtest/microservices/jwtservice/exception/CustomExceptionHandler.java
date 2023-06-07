package com.pmsystemtest.microservices.jwtservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(UserNotFoundException ex) {
        String errorMessage = "user not found";
        String exceptionType = ex.getClass().getSimpleName();
        BaseResponse baseResponse = BaseResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .exceptionType(exceptionType)
                .message(errorMessage)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(DuplicateEmailException ex) {
        String errorMessage = "email is already in use";
        String exceptionType = ex.getClass().getSimpleName();
        BaseResponse baseResponse = BaseResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .exceptionType(exceptionType)
                .message(errorMessage)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MissingTokenException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(MissingTokenException ex) {
        String errorMessage = "missing token";
        String exceptionType = ex.getClass().getSimpleName();
        BaseResponse baseResponse = BaseResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .exceptionType(exceptionType)
                .message(errorMessage)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }
}

package com.pmsystemtest.microservices.pmsservice.exceptions;

import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.*;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.BaseResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.Timestamp;

@ControllerAdvice
public class CustomResponseEntityHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(FeignException.NotFound ex, WebRequest request) {
        String errorMessage = "User not found with id";
        String exceptionType = ex.getClass().getSimpleName();
        BaseResponse baseResponse = BaseResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .exceptionType(exceptionType)
                .message(errorMessage)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(TransactionNotFoundException transactionNotFoundException) {
        String exceptionType = transactionNotFoundException.getClass().getSimpleName();
        String message = "transaction not found";
        int statusCode = determineStatusCode(exceptionType);
        BaseResponse baseResponse = BaseResponse.builder()
                .status(statusCode)
                .exceptionType(exceptionType)
                .message(message)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(PortfolioNotFoundException portfolioNotFoundException) {
        String exceptionType = portfolioNotFoundException.getClass().getSimpleName();
        String message = "portfolio not found";
        int statusCode = determineStatusCode(exceptionType);
        BaseResponse baseResponse = BaseResponse.builder()
                .status(statusCode)
                .exceptionType(exceptionType)
                .message(message)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssetNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(AssetNotFoundException assetNotFoundException) {
        String exceptionType = assetNotFoundException.getClass().getSimpleName();
        String message = "asset not found";
        int statusCode = determineStatusCode(exceptionType);
        BaseResponse baseResponse = BaseResponse.builder()
                .status(statusCode)
                .exceptionType(exceptionType)
                .message(message)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AssetTypeNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(AssetTypeNotFoundException assetTypeNotFoundException) {
        String exceptionType = assetTypeNotFoundException.getClass().getSimpleName();
        String message = "asset type not found";
        int statusCode = determineStatusCode(exceptionType);
        BaseResponse baseResponse = BaseResponse.builder()
                .status(statusCode)
                .exceptionType(exceptionType)
                .message(message)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionTypeNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(TransactionTypeNotFoundException transactionTypeNotFoundException) {
        String exceptionType = transactionTypeNotFoundException.getClass().getSimpleName();
        String message = "transaction type not found";
        int statusCode = determineStatusCode(exceptionType);
        BaseResponse baseResponse = BaseResponse.builder()
                .status(statusCode)
                .exceptionType(exceptionType)
                .message(message)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(CurrencyNotFoundException currencyNotFoundException) {
        String exceptionType = currencyNotFoundException.getClass().getSimpleName();
        String message = "currency not found";
        int statusCode = determineStatusCode(exceptionType);
        BaseResponse baseResponse = BaseResponse.builder()
                .status(statusCode)
                .exceptionType(exceptionType)
                .message(message)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid userId format. userId should be a number.";
        String exceptionType = ex.getClass().getSimpleName();
        int statusCode = HttpStatus.BAD_REQUEST.value();
        BaseResponse baseResponse = BaseResponse.builder()
                .status(statusCode)
                .exceptionType(exceptionType)
                .message(message)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }









    private int determineStatusCode(String exceptionType) {
        if (exceptionType.equals("TransactionNotFoundException") ||
                exceptionType.equals("PortfolioNotFoundException") ||
                exceptionType.equals("AssetNotFoundException") ||
                exceptionType.equals("AssetTypeNotFoundException") || exceptionType.equals("TransactionTypeNotFoundException") || exceptionType.equals("CurrencyNotFoundException")) {
            return HttpStatus.NOT_FOUND.value();
        } else if (exceptionType.equals("TransactionNoContentException")) {
            return HttpStatus.NO_CONTENT.value();
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }
}

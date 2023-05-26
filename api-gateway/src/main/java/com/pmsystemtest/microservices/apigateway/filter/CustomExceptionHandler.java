package com.pmsystemtest.microservices.apigateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmsystemtest.microservices.apigateway.exception.BaseResponse;
import com.pmsystemtest.microservices.apigateway.exception.MissingTokenException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@Component
public class CustomExceptionHandler implements ErrorWebExceptionHandler {

    ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof MissingTokenException) {
            // MissingTokenException hatasıyla ilgili işlemleri yapın
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            // JSON yanıt oluşturma
            String errorMessage = "Missing Token";
            try {
                return getVoidMono(exchange, errorMessage, ex.getClass().getSimpleName(), (Exception) ex);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        } else if (ex instanceof WebClientResponseException.Forbidden) {

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            // JSON yanıt oluşturma
            String errorMessage = "Invalid JWT token";
            try {
                return getVoidMono(exchange, errorMessage, ex.getClass().getSimpleName(), (Exception) ex);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        // Diğer hatalar için varsayılan işlemi yapabilirsiniz
        return Mono.error(ex);
    }

    private Mono<Void> getVoidMono(ServerWebExchange exchange, String errorMessage, String simpleName, Exception ex) throws JsonProcessingException {
        String exceptionType = simpleName;
        BaseResponse baseResponse = BaseResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .exceptionType(exceptionType)
                .message(errorMessage)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(objectMapper.writeValueAsBytes(baseResponse));
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}

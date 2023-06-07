package com.pmsystemtest.microservices.apigateway.filter;

import com.pmsystemtest.microservices.apigateway.exception.MissingTokenException;
import com.pmsystemtest.microservices.apigateway.proxy.ValidateRequest;
import com.pmsystemtest.microservices.apigateway.proxy.ValidateResponse;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenValidationFilter implements GatewayFilter {

    private final WebClient webClient;
    private final String tokenHeader = "Authorization";



    public TokenValidationFilter() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:7432/api/v1/auth")
                .build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = extractTokenFromRequest(exchange.getRequest());

        if (token == null) {

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            throw new MissingTokenException();
        }

        return validateToken(token)
                .flatMap(isValid -> {
                    if (isValid) {
                        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                .header(tokenHeader, "Bearer " + token)
                                .build();
                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                });


    }


    private String extractTokenFromRequest(ServerHttpRequest request) {
        final String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Mono<Boolean> validateToken(String token) {
        ValidateRequest validateRequest = new ValidateRequest();
        validateRequest.setToken(token);

        return webClient.post()
                .uri("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validateRequest)
                .retrieve()
                .bodyToMono(ValidateResponse.class)
                .map(ValidateResponse::isStatus)
                .defaultIfEmpty(false);
    }


}

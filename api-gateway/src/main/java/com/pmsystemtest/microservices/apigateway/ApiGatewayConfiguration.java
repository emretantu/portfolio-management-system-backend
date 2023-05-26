package com.pmsystemtest.microservices.apigateway;

import com.pmsystemtest.microservices.apigateway.filter.TokenValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ApiGatewayConfiguration {

    private final TokenValidationFilter tokenValidationFilter;


    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){

        final String BASE_URI_PMS = "lb://pms-service";
        final String BASE_URI_JWT = "lb://jwt-service";

        return builder.routes()
                .route("user-info-service", p -> p.path("/api/v1/users/**")
                        .filters(f -> f.filter(tokenValidationFilter))
                        .uri("lb://user-info-service"))
                .route("jwt-service", p -> p.path("/api/v1/auth/**").and().method("GET", "POST").uri(BASE_URI_JWT))
                .route("pms-service-portfolios", p -> p.path("/api/v1/portfolios/**")
                        .and().method("GET", "POST", "PATCH")
                        .filters(f -> f.filter(tokenValidationFilter))
                        .uri(BASE_URI_PMS))
                .route("pms-service-transactions", p -> p.path("/api/v1/transactions/**")
                        .and().method("GET", "POST", "DELETE")
                        .filters(f -> f.filter(tokenValidationFilter))
                        .uri(BASE_URI_PMS))
                .route("pms-service-assets", p -> p.path("/api/v1/assets/**")
                        .and().method("GET", "POST", "PATCH")
                        .filters(f -> f.filter(tokenValidationFilter))
                        .uri(BASE_URI_PMS))
                .route("pms-service-asset-tracks", p -> p.path("/api/v1/asset-tracks/**")
                        .and().method("POST")
                        .filters(f -> f.filter(tokenValidationFilter))
                        .uri(BASE_URI_PMS))
                .route("pms-service-asset-types", p -> p.path("/api/v1/asset-types/**")
                        .and().method("GET", "POST", "PATCH")
                        .filters(f -> f.filter(tokenValidationFilter))
                        .uri(BASE_URI_PMS))
                .route("pms-service-transaction-types", p -> p.path("/api/v1/transaction-types/**")
                        .and().method("GET")
                        .filters(f -> f.filter(tokenValidationFilter))
                        .uri(BASE_URI_PMS))
                .route("pms-service-currencies", p -> p.path("/api/v1/currencies/**")
                        .and().method("GET")
                        .filters(f -> f.filter(tokenValidationFilter))
                        .uri(BASE_URI_PMS))
                .build();

    }








}

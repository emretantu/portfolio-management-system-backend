package com.pmsystemtest.microservices.pmsservice.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioRequest {

    private Long userId;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Currency ID cannot be null")
    private Long currencyId;

    @NotNull(message = "Initial Change of Cost cannot be null")
    @Min(value = 0, message = "Initial Change of Cost must be a non-negative value")
    private BigInteger initialChangeOfCost;
}

package com.pmsystemtest.microservices.pmsservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long id;

    @NotNull(message = "Portfolio ID cannot be null")
    private Long portfolioId;

    @NotNull(message = "Asset ID cannot be null")
    private Long assetId;

    @NotNull(message = "Transaction Type ID cannot be null")
    private Long transactionTypeId;

    @NotNull(message = "Change of Quantity cannot be null")
    private BigDecimal changeOfQuantity;

    @NotNull(message = "Change of Main Cost cannot be null")
    private BigInteger changeOfMainCost;

    @NotNull(message = "Change of Portfolio Cost cannot be null")
    private BigDecimal changeOfPortfolioCost;

    @PastOrPresent(message = "Time must be in the past or present")
    private Timestamp time;
}

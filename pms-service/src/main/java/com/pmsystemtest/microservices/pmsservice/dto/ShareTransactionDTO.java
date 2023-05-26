package com.pmsystemtest.microservices.pmsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareTransactionDTO
{

    private Long id;

    private BigDecimal changeOfQuantity;

    private BigInteger changeOfCost;

    private Timestamp timestamp;
}

package com.pmsystemtest.microservices.pmsservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "transaction_buffer")
@Entity
public class TransactionBuffer {

    @Id
    private Long id;

    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "asset_id")
    private Long assetId;

    @Column(name = "transaction_type_id")
    private Long transactionTypeId;

    @Column(name = "change_of_quantity")
    private BigDecimal changeOfQuantity;

    @Column(name = "change_of_main_cost")
    private BigInteger changeOfMainCost;

    @Column(name = "change_of_portfolio_cost")
    private BigDecimal changeOfPortfolioCost;

    @Column(name = "time")
    private Timestamp time;

}

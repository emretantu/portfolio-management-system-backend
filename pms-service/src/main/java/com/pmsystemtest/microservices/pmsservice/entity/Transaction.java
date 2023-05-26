package com.pmsystemtest.microservices.pmsservice.entity;

import jakarta.persistence.*;
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
@Table(name = "transaction")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    private Asset asset;
    @ManyToOne
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    private TransactionType transactionType;

    @Column(name = "change_of_quantity")
    private BigDecimal changeOfQuantity;

    @Column(name = "change_of_main_cost")
    private BigInteger changeOfMainCost;

    @Column(name = "change_of_portfolio_cost")
    private BigDecimal changeOfPortfolioCost;

    @Column(name = "time")
    private Timestamp time;
}

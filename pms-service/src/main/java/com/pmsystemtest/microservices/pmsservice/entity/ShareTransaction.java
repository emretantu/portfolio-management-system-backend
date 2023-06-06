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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="share_transaction")
public class ShareTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Column(name = "change_of_quantity")
    private BigDecimal changeOfQuantity;

    @Column(name = "change_of_cost", columnDefinition = "bigint")
    private BigInteger changeOfCost;

    @Column(name = "time")
    private Timestamp time;
}

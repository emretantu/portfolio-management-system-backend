package com.pmsystemtest.microservices.pmsservice.dto;

import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioShareTransactionDTO {

    private Long id;

    private Long userId;

    private String name;

    private Long currencyId;

    private List<ShareTransactionDTO> shareTransactions;
}

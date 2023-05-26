package com.pmsystemtest.microservices.pmsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDTO {

    /*private Long portfolioId;
    private Long userId;*/
    private String name;

    private Long currencyId;

    private Boolean status;

}

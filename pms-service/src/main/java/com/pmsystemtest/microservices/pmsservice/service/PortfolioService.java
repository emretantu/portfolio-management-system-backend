package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.dto.PortfolioDTO;
import com.pmsystemtest.microservices.pmsservice.dto.PortfolioShareTransactionDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Portfolio;
import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import com.pmsystemtest.microservices.pmsservice.request.PortfolioRequest;
import org.springframework.http.ResponseEntity;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

public interface PortfolioService {

    List<Portfolio> findAll();

    Portfolio findById(Long portfolio_id);

    Portfolio createPortfolio(PortfolioRequest portfolio, Long userId);
    ResponseEntity<SuccessResponse> updatePortfolio(PortfolioDTO portfolioDTO, Long portfolioId, String token);

    List<Portfolio> findByUserId(Long userId);

    List<PortfolioShareTransactionDTO> getAllPortfolioShareTransactionDtos(Long userId);
    List<PortfolioShareTransactionDTO> getAllPortfolioShareTransactionDtos();

    List<ShareTransaction> getAllShareTransactionByPortfolioId(Long portfolioId);

    Portfolio findUserIdById(Long portfolioId);


}

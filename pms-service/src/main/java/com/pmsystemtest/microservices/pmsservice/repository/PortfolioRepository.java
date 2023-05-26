package com.pmsystemtest.microservices.pmsservice.repository;

import com.pmsystemtest.microservices.pmsservice.entity.Portfolio;
import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByUserId(Long user_id);

    Optional<Portfolio> findByUserIdAndId(Long user_id, Long portfolio_id);


}

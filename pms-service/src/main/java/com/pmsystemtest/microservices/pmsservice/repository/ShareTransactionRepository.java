package com.pmsystemtest.microservices.pmsservice.repository;

import com.pmsystemtest.microservices.pmsservice.entity.ShareTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareTransactionRepository extends JpaRepository<ShareTransaction, Long> {
    List<ShareTransaction> findAll();
}

package com.pmsystemtest.microservices.pmsservice.repository;

import com.pmsystemtest.microservices.pmsservice.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {


}

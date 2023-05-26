package com.pmsystemtest.microservices.pmsservice.repository;

import com.pmsystemtest.microservices.pmsservice.entity.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {
}

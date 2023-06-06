package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.dto.AssetDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Asset;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AssetService {

    List<AssetDTO> findAssetsByUserId(Long userId);

    List<Asset> findAll();

    Asset createAsset(AssetDTO assetDTO, Long userId);

    ResponseEntity<SuccessResponse> updateAsset(AssetDTO assetDTO, Long assetId, String token);
}

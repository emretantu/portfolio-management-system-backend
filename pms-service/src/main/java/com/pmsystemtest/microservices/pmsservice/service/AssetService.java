package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.dto.AssetDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Asset;

import java.util.List;

public interface AssetService {

    List<AssetDTO> findAssetsByUserId(Long userId);

    List<Asset> findAll();

    Asset createAsset(AssetDTO assetDTO, Long userId);

    Asset updateAsset(AssetDTO assetDTO, Long assetId);
}

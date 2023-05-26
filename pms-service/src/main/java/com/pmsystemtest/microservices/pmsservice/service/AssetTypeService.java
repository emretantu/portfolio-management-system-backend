package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.dto.AssetTypeDTO;
import com.pmsystemtest.microservices.pmsservice.entity.AssetType;

import java.util.List;

public interface AssetTypeService {

    List<AssetType> findAll();

    List<AssetType> findAllAssetTypesByUserId(Long userId);

    AssetType findById(Long assetId);

    AssetType save(AssetTypeDTO assetTypeDTO);

    AssetType update(AssetType assetType);

    void deleteById(Long asset_id);
}

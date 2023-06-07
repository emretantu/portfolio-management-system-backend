package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.dto.AssetTypeDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Asset;
import com.pmsystemtest.microservices.pmsservice.entity.AssetType;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.AssetNotFoundException;
import com.pmsystemtest.microservices.pmsservice.repository.AssetRepository;
import com.pmsystemtest.microservices.pmsservice.repository.AssetTypeRepository;
import com.pmsystemtest.microservices.pmsservice.service.AssetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AssetTypeServiceImpl implements AssetTypeService {

    private final AssetTypeRepository theAssetTypeRepository;
    private final AssetRepository theAssetRepository;

    @Override
    public List<AssetType> findAll() {
        return theAssetTypeRepository.findAll();
    }

    @Override
    public List<AssetType> findAllAssetTypesByUserId(Long userId) {
        List<Asset> assets = theAssetRepository.findAssetsByUserId(userId);
        if(assets.isEmpty()){
            throw new AssetNotFoundException();
        }
        Set<AssetType> assetTypes = new HashSet<>();
        for(Asset asset : assets){
            assetTypes.add(asset.getAssetType());
        }
        return new ArrayList<>(assetTypes);
    }

    @Override
    public AssetType findById(Long assetId) {
        Optional<AssetType> assetTypeOptional = theAssetTypeRepository.findById(assetId);
        AssetType assetType = null;
        if(assetTypeOptional.isPresent()){
            assetType = assetTypeOptional.get();
        }else{
            throw new AssetNotFoundException();
        }
        return assetType;
    }

    @Override
    public AssetType save(AssetTypeDTO assetTypeDTO) {
        AssetType assetType = AssetType.builder()
                .id(0L)
                .name(assetTypeDTO.getName())
                .build();

        return theAssetTypeRepository.save(assetType);
    }

    @Override
    public AssetType update(AssetType assetType) {
        return theAssetTypeRepository.save(assetType);
    }

    @Override
    public void deleteById(Long asset_id) {
        theAssetTypeRepository.deleteById(asset_id);

    }
}

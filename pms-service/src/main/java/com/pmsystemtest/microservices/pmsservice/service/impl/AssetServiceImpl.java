package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.dto.AssetDTO;
import com.pmsystemtest.microservices.pmsservice.dto.AssetTrackDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Asset;
import com.pmsystemtest.microservices.pmsservice.entity.AssetType;
import com.pmsystemtest.microservices.pmsservice.entity.Currency;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.AssetNotFoundException;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.AssetTypeNotFoundException;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.CurrencyNotFoundException;
import com.pmsystemtest.microservices.pmsservice.repository.AssetRepository;
import com.pmsystemtest.microservices.pmsservice.repository.AssetTrackRepository;
import com.pmsystemtest.microservices.pmsservice.repository.AssetTypeRepository;
import com.pmsystemtest.microservices.pmsservice.repository.CurrencyRepository;
import com.pmsystemtest.microservices.pmsservice.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository theAssetRepository;
    private final AssetTrackRepository theAssetTrackRepository;
    private final CurrencyRepository theCurrencyRepository;
    private final AssetTypeRepository theAssetTypeRepository;



    @Override
    public List<AssetDTO> findAssetsByUserId(Long userId) {
        List<AssetDTO> assetDTOList = new ArrayList<>();
        List<Asset> assets = theAssetRepository.findAssetsByUserId(userId);
        for(Asset asset : assets){
            List<AssetTrackDTO> assetTrackDTOList = asset.getAssetTrackList().stream()
                    .map(_asset -> AssetTrackDTO.builder()
                            .id(_asset.getId())
                            .assetId(_asset.getAsset().getId())
                            .value(_asset.getValue())
                            .time(new Timestamp(System.currentTimeMillis()))
                            .build()
                    ).collect(Collectors.toList());


            AssetDTO assetDTO = AssetDTO.builder()
                    .id(asset.getId())
                    .name(asset.getName())
                    .currencyId(asset.getCurrency().getId())
                    .assetTypeId(asset.getAssetType().getId())
                    .userId(asset.getUserId())
                    .status(asset.getStatus())
                    .assetTrackList(assetTrackDTOList)
                    .build();
            assetDTOList.add(assetDTO);
        }
        if(assetDTOList.isEmpty()){
            throw new AssetNotFoundException();
        }
        return assetDTOList;
    }



    @Override
    public List<Asset> findAll() {
        return theAssetRepository.findAll();
    }

    @Override
    public Asset createAsset(AssetDTO assetDTO, Long userId) {
        Optional<Currency> currencyOptional = theCurrencyRepository.findById(assetDTO.getCurrencyId());
        Currency currency = null;
        if(currencyOptional.isPresent()){
            currency = currencyOptional.get();
        }else{
            throw new CurrencyNotFoundException();
        }

        Optional<AssetType> assetTypeOptional = theAssetTypeRepository.findById(assetDTO.getAssetTypeId());
        AssetType assetType = null;
        if(assetTypeOptional.isPresent()){
            assetType = assetTypeOptional.get();
        }else{
            throw new AssetTypeNotFoundException();
        }

        Asset asset = Asset.builder()
                .name(assetDTO.getName())
                .currency(currency)
                .assetType(assetType)
                .userId(userId)
                .status(assetDTO.getStatus())
                .build();
        theAssetRepository.save(asset);

        return asset;
    }

    @Override
    public Asset updateAsset(AssetDTO assetDTO, Long assetId) {

        Optional<Asset> assetOptional = theAssetRepository.findById(assetId);
        Asset asset = null;
        if(assetOptional.isPresent()){
            asset = assetOptional.get();
        }else{
            throw new AssetNotFoundException();
        }

        if(assetDTO.getName() != null){
            asset.setName(assetDTO.getName());
        }

        if(assetDTO.getCurrencyId() != null){
            Optional<Currency> currencyOptional = theCurrencyRepository.findById(assetDTO.getCurrencyId());
            Currency currency = null;
            if(currencyOptional.isPresent()){
                currency = currencyOptional.get();
            }else{
                throw new CurrencyNotFoundException();
            }
            asset.setCurrency(currency);
        }

        if(assetDTO.getAssetTypeId() != null){
            Optional<AssetType> assetTypeOptional = theAssetTypeRepository.findById(assetDTO.getAssetTypeId());
            AssetType assetType = null;
            if(assetTypeOptional.isPresent()){
                assetType = assetTypeOptional.get();
            }else{
                throw new AssetTypeNotFoundException();
            }
            asset.setAssetType(assetType);
        }

        if(assetDTO.getStatus() != null){
            asset.setStatus(assetDTO.getStatus());
        }
        return theAssetRepository.save(asset);
    }
}

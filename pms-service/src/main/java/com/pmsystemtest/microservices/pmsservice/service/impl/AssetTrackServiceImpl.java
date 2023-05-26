package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.dto.AssetTrackDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Asset;
import com.pmsystemtest.microservices.pmsservice.entity.AssetTrack;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.AssetNotFoundException;
import com.pmsystemtest.microservices.pmsservice.repository.AssetRepository;
import com.pmsystemtest.microservices.pmsservice.repository.AssetTrackRepository;
import com.pmsystemtest.microservices.pmsservice.service.AssetTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetTrackServiceImpl implements AssetTrackService {

    private final AssetTrackRepository theAssetTrackRepository;
    private final AssetRepository theAssetRepository;

    @Override
    public ResponseEntity<AssetTrack> createAssetTrack(AssetTrackDTO assetTrackDTO, Long assetId) {
        Optional<Asset> assetOptional = theAssetRepository.findById(assetId);
        Asset asset = null;
        if(assetOptional.isPresent()){
            asset = assetOptional.get();
        }else{
            throw new AssetNotFoundException();
        }

        AssetTrack assetTrack = AssetTrack.builder()
                .asset(asset)
                .value(assetTrackDTO.getValue())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        assetTrack = theAssetTrackRepository.save(assetTrack);
        return new ResponseEntity<>(assetTrack, HttpStatus.CREATED);

    }
}

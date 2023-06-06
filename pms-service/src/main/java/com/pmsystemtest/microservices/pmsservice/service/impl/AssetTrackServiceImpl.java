package com.pmsystemtest.microservices.pmsservice.service.impl;

import com.pmsystemtest.microservices.pmsservice.config.TokenValidator;
import com.pmsystemtest.microservices.pmsservice.dto.AssetTrackDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Asset;
import com.pmsystemtest.microservices.pmsservice.entity.AssetTrack;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.AssetNotFoundException;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import com.pmsystemtest.microservices.pmsservice.repository.AssetRepository;
import com.pmsystemtest.microservices.pmsservice.repository.AssetTrackRepository;
import com.pmsystemtest.microservices.pmsservice.service.AssetTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetTrackServiceImpl implements AssetTrackService {

    private final AssetTrackRepository theAssetTrackRepository;
    private final AssetRepository theAssetRepository;
    private final TokenValidator tokenValidator;

    @Override
    public ResponseEntity<SuccessResponse> createAssetTrack(AssetTrackDTO assetTrackDTO, Long assetId, String token) {

        Asset asset = theAssetRepository.findUserIdById(assetId).orElseThrow(AssetNotFoundException::new);
        if(!tokenValidator.checkTokenByUserId(asset.getUserId(), token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user ID");
        }

        AssetTrack assetTrack = AssetTrack.builder()
                .asset(asset)
                .value(assetTrackDTO.getValue())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        assetTrack = theAssetTrackRepository.save(assetTrack);

        SuccessResponse successResponse = SuccessResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Asset Track created successfully")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);

    }
}

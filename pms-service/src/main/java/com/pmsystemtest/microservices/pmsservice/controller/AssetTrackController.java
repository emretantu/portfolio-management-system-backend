package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.dto.AssetTrackDTO;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import com.pmsystemtest.microservices.pmsservice.service.AssetTrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1/asset-tracks")
@RequiredArgsConstructor
@CrossOrigin
public class AssetTrackController {

    private final AssetTrackService theAssetTrackService;

    @PostMapping("/{assetId}")
    public ResponseEntity<SuccessResponse> createAssetTrackData(@PathVariable Long assetId, @Valid @RequestBody AssetTrackDTO assetTrackDTO){

        SuccessResponse successResponse = null;
        if(theAssetTrackService.createAssetTrack(assetTrackDTO, assetId) != null){
            successResponse = SuccessResponse.builder()
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Asset Track created successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

}

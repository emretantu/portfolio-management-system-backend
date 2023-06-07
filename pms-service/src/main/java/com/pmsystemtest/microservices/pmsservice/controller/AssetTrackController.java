package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.config.TokenValidator;
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
    public ResponseEntity<SuccessResponse> createAssetTrackData(
            @PathVariable Long assetId,
            @Valid @RequestBody AssetTrackDTO assetTrackDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        String token = authorizationHeader.substring(7);

        return theAssetTrackService.createAssetTrack(assetTrackDTO, assetId, token);
    }

}

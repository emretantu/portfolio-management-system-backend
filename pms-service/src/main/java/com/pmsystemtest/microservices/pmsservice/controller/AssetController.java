package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.config.TokenValidator;
import com.pmsystemtest.microservices.pmsservice.dto.AssetDTO;
import com.pmsystemtest.microservices.pmsservice.entity.Asset;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.AssetPostResponse;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import com.pmsystemtest.microservices.pmsservice.proxy.UserProxy;
import com.pmsystemtest.microservices.pmsservice.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
@CrossOrigin
public class AssetController {

    private final AssetService theAssetService;
    private final UserProxy theUserProxy;
    private final TokenValidator tokenValidator;



    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<AssetDTO> retrieveAssetsByUserId(
            @RequestHeader("Authorization") String authorizationHeader
    ){
        String token = authorizationHeader.substring(7);
        Long userId = tokenValidator.getUserIdByToken(token);
        theUserProxy.isExistUser(userId);

        return theAssetService.findAssetsByUserId(userId);
    }

    @PostMapping()
    public ResponseEntity<AssetPostResponse> createAsset(
            @Valid @RequestBody AssetDTO assetDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        String token = authorizationHeader.substring(7);
        Long userId = tokenValidator.getUserIdByToken(token);
        theUserProxy.isExistUser(userId);

        AssetPostResponse assetPostResponse  = null;
        Asset asset = theAssetService.createAsset(assetDTO, userId);
        if(asset != null){
            assetPostResponse = AssetPostResponse.builder()
                    .id(asset.getId())
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Asset created successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return new ResponseEntity<>(assetPostResponse, HttpStatus.CREATED);

    }

    @PatchMapping("/{assetId}")
    public ResponseEntity<SuccessResponse> updateAsset(
            @PathVariable Long assetId,
            @RequestBody AssetDTO assetDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        String token = authorizationHeader.substring(7);
        return theAssetService.updateAsset(assetDTO, assetId, token);

    }

}

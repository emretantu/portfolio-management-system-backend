package com.pmsystemtest.microservices.pmsservice.controller;

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

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
@CrossOrigin
public class AssetController {

    private final AssetService theAssetService;
    private final UserProxy theUserProxy;



    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AssetDTO> retrieveAssetsByUserId(@PathVariable Long userId){

        boolean status = theUserProxy.isExistUser(userId);
        if(!status){
            throw new RuntimeException("hatali giris");
        }
        return theAssetService.findAssetsByUserId(userId);


    }

    @PostMapping("/{userId}")
    public ResponseEntity<AssetPostResponse> createAsset(@PathVariable Long userId, @Valid @RequestBody AssetDTO assetDTO){
        boolean status = theUserProxy.isExistUser(userId);
        if(!status){
            throw new RuntimeException("hatali giris");
        }

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
    public ResponseEntity<SuccessResponse> updateAsset(@PathVariable Long assetId, @RequestBody AssetDTO assetDTO){

        SuccessResponse successResponse = null;
        if(theAssetService.updateAsset(assetDTO, assetId) != null){
            successResponse = SuccessResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Asset updated successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return new ResponseEntity<>(successResponse, HttpStatus.OK);

    }

    @GetMapping
    public List<Asset> retrieveAllAssets(){
        return theAssetService.findAll();
    }
}

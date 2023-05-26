package com.pmsystemtest.microservices.pmsservice.controller;

import com.pmsystemtest.microservices.pmsservice.dto.AssetTypeDTO;
import com.pmsystemtest.microservices.pmsservice.entity.AssetType;
import com.pmsystemtest.microservices.pmsservice.exceptions.customexceptions.AssetTypeNotFoundException;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.AssetPostResponse;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import com.pmsystemtest.microservices.pmsservice.proxy.UserProxy;
import com.pmsystemtest.microservices.pmsservice.request.AssetTypePatchRequest;
import com.pmsystemtest.microservices.pmsservice.service.AssetTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/asset-types")
@RequiredArgsConstructor
@CrossOrigin
public class AssetTypeController {

    private final AssetTypeService theAssetTypeService;
    private final UserProxy theUserProxy;



    @GetMapping
    public List<AssetType> retrieveAllAssets(){
        return theAssetTypeService.findAll();
    }

    @GetMapping("/{userId}")
    public List<AssetType> retrieveAssetTypesByUserId(@PathVariable Long userId){
        boolean status = theUserProxy.isExistUser(userId);
        if(!status){
            throw new RuntimeException("hatali giris");
        }

        return theAssetTypeService.findAllAssetTypesByUserId(userId);
    }

    /*@GetMapping("/{assetId}")
    public AssetType retrieveOneAsset(@PathVariable Long assetId){
        return theAssetTypeService.findById(assetId);
    }*/

    @PostMapping
    public ResponseEntity<AssetPostResponse> createAsset(@Valid @RequestBody AssetTypeDTO assetTypeDTO){
        AssetPostResponse assetPostResponse  = null;

        AssetType assetType = theAssetTypeService.save(assetTypeDTO);
        if(assetType != null){
            assetPostResponse = AssetPostResponse.builder()
                    .id(assetType.getId())
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Asset Type created successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return new ResponseEntity<>(assetPostResponse, HttpStatus.CREATED);

    }

    @PatchMapping("/{assetTypeId}")
    public ResponseEntity<SuccessResponse> updateAsset(@Valid @RequestBody AssetTypePatchRequest patchRequest, @PathVariable Long assetTypeId){
        AssetType existingAssetType = theAssetTypeService.findById(assetTypeId);
        if(existingAssetType == null){
            throw new AssetTypeNotFoundException();
        }
        if(patchRequest.getName() != null){
            existingAssetType.setName(patchRequest.getName());
        }

        SuccessResponse successResponse = null;
        if(theAssetTypeService.update(existingAssetType) != null){
            successResponse = SuccessResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Asset Type updated successfully")
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return new ResponseEntity<>(successResponse, HttpStatus.OK);


    }

}

package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.dto.AssetTrackDTO;
import com.pmsystemtest.microservices.pmsservice.entity.AssetTrack;
import com.pmsystemtest.microservices.pmsservice.exceptions.responses.SuccessResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AssetTrackService {

    //List<AssetTrack> findAssetTracksByAssetId(Long assetId);

    ResponseEntity<SuccessResponse> createAssetTrack(AssetTrackDTO assetTrackDTO, Long assetId, String token);
}

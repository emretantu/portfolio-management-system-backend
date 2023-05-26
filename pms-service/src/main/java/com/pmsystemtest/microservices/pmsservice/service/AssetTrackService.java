package com.pmsystemtest.microservices.pmsservice.service;

import com.pmsystemtest.microservices.pmsservice.dto.AssetTrackDTO;
import com.pmsystemtest.microservices.pmsservice.entity.AssetTrack;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AssetTrackService {

    //List<AssetTrack> findAssetTracksByAssetId(Long assetId);

    ResponseEntity<AssetTrack> createAssetTrack(AssetTrackDTO assetTrackDTO, Long assetId);
}

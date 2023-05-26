package com.pmsystemtest.microservices.pmsservice.repository;

import com.pmsystemtest.microservices.pmsservice.entity.AssetTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetTrackRepository extends JpaRepository<AssetTrack, Long> {

    List<AssetTrack> findAssetTracksByAssetId(Long assetId);
}

package com.pmsystemtest.microservices.pmsservice.dto;

import com.pmsystemtest.microservices.pmsservice.entity.AssetTrack;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetDTO {

    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Currency ID cannot be null")
    private Long currencyId;

    @NotNull(message = "Asset Type ID cannot be null")
    private Long assetTypeId;


    private Long userId;

    @NotNull(message = "Status cannot be null")
    private Boolean status;

    private List<AssetTrackDTO> assetTrackList;


}

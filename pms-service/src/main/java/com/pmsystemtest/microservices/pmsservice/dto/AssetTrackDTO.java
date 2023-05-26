package com.pmsystemtest.microservices.pmsservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AssetTrackDTO {
    private Long id;
    private Long assetId;

    @NotNull(message = "Value cannot be null")
    private Long value;

    private Timestamp time;
}

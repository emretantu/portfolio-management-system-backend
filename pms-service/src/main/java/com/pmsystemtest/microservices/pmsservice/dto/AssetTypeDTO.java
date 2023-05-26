package com.pmsystemtest.microservices.pmsservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetTypeDTO {

    @NotNull(message = "name cannot be null")
    private String name;
}

package com.pmsystemtest.microservices.pmsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "asset")
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")

    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "asset_type_id", referencedColumnName = "id")

    private AssetType assetType;

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "asset",  cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AssetTrack> assetTrackList;


}

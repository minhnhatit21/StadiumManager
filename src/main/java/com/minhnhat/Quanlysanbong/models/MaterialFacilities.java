package com.minhnhat.Quanlysanbong.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class MaterialFacilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nameMaterialFacilities;

    @NotNull
    private Integer quantity;

    @NotBlank
    private String statusMaterialFacilities;

}

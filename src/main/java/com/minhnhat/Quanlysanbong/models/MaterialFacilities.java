package com.minhnhat.Quanlysanbong.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class MaterialFacilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String materialName;

    @NotNull
    private Integer quantityOnHand;

    @NotNull
    private Date maintenanceSchedule;

    @NotNull
    private Date lastMaintenanceDate;

}

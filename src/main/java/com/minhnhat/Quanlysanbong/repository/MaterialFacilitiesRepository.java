package com.minhnhat.Quanlysanbong.repository;

import com.minhnhat.Quanlysanbong.models.Beverage;
import com.minhnhat.Quanlysanbong.models.MaterialFacilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialFacilitiesRepository extends JpaRepository<MaterialFacilities, Long> {
    @Query(value = "SELECT m FROM MaterialFacilities m WHERE (m.id = :materialFacilitiesID)")
    MaterialFacilities findMaterialFacilitiesById(Long materialFacilitiesID);
    @Query(value = "SELECT b FROM Beverage b WHERE (b.name = :beverageName)")
    MaterialFacilities findMaterialFacilitiesName(String beverageName);
}

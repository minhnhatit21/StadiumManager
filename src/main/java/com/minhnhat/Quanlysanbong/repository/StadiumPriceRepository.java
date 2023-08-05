package com.minhnhat.Quanlysanbong.repository;

import com.minhnhat.Quanlysanbong.models.StadiumPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumPriceRepository extends JpaRepository<StadiumPrice, Long> {
    @Query("SELECT e FROM StadiumPrice e ")
    List<StadiumPrice> findAllDetails();

    @Query(value = "SELECT e FROM StadiumPrice e WHERE(e.id = :stadiumDetailID)")
    StadiumPrice findStadiumById(Long stadiumDetailID);
}

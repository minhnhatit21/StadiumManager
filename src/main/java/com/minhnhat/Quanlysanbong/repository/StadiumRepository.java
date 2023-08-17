package com.minhnhat.Quanlysanbong.repository;

import com.minhnhat.Quanlysanbong.models.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    @Query("SELECT e FROM Stadium e " +
            "LEFT OUTER JOIN StadiumPrice s ON s.stadium.id = e.id")
    List<Stadium> findAllWithDetails();
}

package com.minhnhat.Quanlysanbong.repository;

import com.minhnhat.Quanlysanbong.models.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Long> {
    @Query(value = "SELECT b FROM Beverage b WHERE (b.id = :beverageID)")
    Beverage findBeverageById(Long beverageID);
    @Query(value = "SELECT b FROM Beverage b WHERE (b.name = :beverageName)")
    Beverage findBeverageName(String beverageName);
}

package com.minhnhat.Quanlysanbong.repository;

import com.minhnhat.Quanlysanbong.models.Authority;
import com.minhnhat.Quanlysanbong.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(ERole name);
}

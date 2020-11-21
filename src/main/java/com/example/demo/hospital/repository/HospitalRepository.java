package com.example.demo.hospital.repository;

import com.example.demo.hospital.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT ho FROM Hospital ho ORDER BY ho.id DESC")
    List<Hospital> findAllDesc();

    @Query("SELECT ho FROM Hospital ho WHERE ho.name = :name")
    Optional<Hospital> findHospital(String name);
}

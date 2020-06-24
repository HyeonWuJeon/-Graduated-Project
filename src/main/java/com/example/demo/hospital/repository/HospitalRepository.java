package com.example.demo.hospital.repository;

import com.example.demo.hospital.vo.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT ho FROM Hospital ho ORDER BY ho.id DESC")
    List<Hospital> findAllDesc();

    @Query("SELECT ho FROM Hospital ho WHERE ho.id = :id")
    Hospital findOne(Long id);

    @Query("SELECT ho FROM Hospital ho WHERE ho.name = :name")
    Hospital findname(String name);
}


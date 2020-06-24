package com.example.demo.disease.dao;

import com.example.demo.disease.domain.Disease;
import com.example.demo.disease.dto.DiseaseNameCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    @Query("SELECT di FROM Disease di ORDER BY di.id DESC")
    List<Disease> findAllDesc();

    @Query("SELECT " +
            "   new com.example.demo.disease.dto.DiseaseNameCountDto(dis.name, COUNT(dis.name))" +
            "FROM " +
            "   Disease dis " +
            "GROUP BY " +
            "   dis.name")
    List<DiseaseNameCountDto> findNameCount();
}

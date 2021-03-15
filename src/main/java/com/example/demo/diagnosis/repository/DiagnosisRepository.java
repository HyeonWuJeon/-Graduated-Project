package com.example.demo.diagnosis.repository;

import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    // 증상 시각화
    @Query("SELECT diag FROM Diagnosis diag where diag.member = :member")
    Diagnosis findMember(Member member);

    @Query("SELECT diag FROM Diagnosis diag where diag.member =:member ORDER BY diag.id DESC ")
    List<Diagnosis> findAllDesc(Member member);

    /**
    기존 쿼리
     */
//    @Query("SELECT " +
//            "   new com.example.demo.diagnosis.dto.DiagnosisNameCountDto(diag.c, COUNT(diag.type(name)))" +
//            "FROM " +
//            "   Diagnosis diag " +
//            "GROUP BY" +
//            "   diag.dog")
//    List<DiagnosisNameCountDto> countByName();

    /**
     * 변경쿼리
     * @return
//     */
//    @Query("SELECT " +
//            "   new com.example.demo.diagnosis.dto.DiagnosisNameCountDto(diag.disease_type, COUNT(diag.dog))" +
//            "FROM " +
//            " Diagnosis diag " +
//            "GROUP BY" +
//            "   diag.dog")
//    List<DiagnosisNameCountDto> countByName();
//
    @Query(value = "SELECT disease_type as diseaseTypeName, COUNT(disease_type) as diseaseTypeCount FROM Diagnosis_table GROUP BY disease_type", nativeQuery = true)
    List<Diagnosis> countByName();

}

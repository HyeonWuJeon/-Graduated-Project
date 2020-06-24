package com.example.demo.diagnosis.repository;

import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    // 증상 시각화
    @Query("SELECT diag FROM Diagnosis diag where diag.member = :member")
    Diagnosis findMember(Member member);

    @Query("SELECT diag FROM Diagnosis diag where diag.member =:member ORDER BY diag.id DESC ")
    List<Diagnosis> findAllDesc(Member member);

    @Query("SELECT " +
            "   new com.example.demo.diagnosis.dto.DiagnosisNameCountDto(diag.name, COUNT(diag.name))" +
            "FROM " +
            "   Diagnosis diag " +
            "GROUP BY " +
            "   diag.name")
    List<DiagnosisNameCountDto> findNameCount();
}

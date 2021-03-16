package com.example.demo.diagnosis.repository;

import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.dto.DiagnosisDto;
import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.dog.domain.Dog;
import com.example.demo.member.domain.Member;
import jdk.jshell.Diag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    // 증상 시각화
//    @Query("SELECT diag FROM Diagnosis diag where diag.member = :member")
//    Diagnosis findMember(Member member);
//
    @Query("SELECT diag FROM Diagnosis diag where diag.member =:member")
    List<Diagnosis> findAllDelete(Member member);


    @Query(value = "SELECT * FROM diagnosis_table diag where diag.member_id = ?1", nativeQuery = true)
    List<Diagnosis> findAllDesc(Long member_id);

    Diagnosis findByDog(Dog dog);

}

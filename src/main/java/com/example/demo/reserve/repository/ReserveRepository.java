package com.example.demo.reserve.repository;

import com.example.demo.hospital.vo.Hospital;
import com.example.demo.member.domain.Member;
import com.example.demo.reserve.vo.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    @Query("SELECT r FROM Reserve r WHERE r.member = :member ORDER BY r.id DESC")
    List<Reserve> findAllMemberDesc(Member member);

    @Query("SELECT r FROM Reserve r ORDER BY r.id DESC")
    List<Reserve> findAll();

    @Query("SELECT r FROM Reserve r WHERE r.hospital = :hospital ORDER BY r.id DESC")
    List<Reserve> findAllHospitalDesc(Hospital hospital);


}

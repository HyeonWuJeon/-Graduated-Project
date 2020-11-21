package com.example.demo.member.repository;

import com.example.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface
MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.role ='GUEST' OR m.role = 'VET' ORDER BY m.id DESC ")
    List<Member> findAllDesc();

    //LINE :: 회원 이메일 조회
    Optional<Member> findByEmail(String email);

}

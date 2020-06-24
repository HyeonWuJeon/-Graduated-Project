package com.example.demo.member.repository;

import com.example.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface MemberRepository extends JpaRepository<Member, Long> {


    @Query("SELECT m FROM Member m WHERE m.role ='GUEST' OR m.role = 'VET' ORDER BY m.id DESC ")
    List<Member> findAllDesc();
    /**
     *
     * @param email
     * @return 이미 회원가입 된 사람 입니다.
     */
    @Query("SELECT m FROM Member m where m.email =  :email")
    Member findEmailCheck(String email);

    /**
     * @param id
     * return 회원찾기
     */
    @Query("SELECT m FROM Member m where m.id = :id")
    Member findOne(Long id);
}

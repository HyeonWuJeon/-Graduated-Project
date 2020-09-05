package com.example.demo.dog.repository;

import com.example.demo.dog.domain.Dog;
import com.example.demo.dog.dto.DogTypeCountDto;
import com.example.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long> {

    @Query("SELECT d FROM Dog d WHERE d.member = :member")
    List<Dog> findAllDesc(Member member);

    @Query("SELECT " +
            "   new com.example.demo.dog.dto.DogTypeCountDto(d.gender, d.type, COUNT(d))" +
            "FROM " +
            "   Dog d " +
            "GROUP BY " +
            "   d.type, d.gender")
    List<DogTypeCountDto> findDogCount();
}

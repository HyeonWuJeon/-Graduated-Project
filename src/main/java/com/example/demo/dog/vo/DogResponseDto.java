package com.example.demo.dog.vo;


import com.example.demo.member.domain.Member;
import lombok.Getter;

@Getter
public class DogResponseDto {
    private Long id;
    private Member member;
    private String name;
    private String age;
    private String gender;
    private String birth;
    private String type;


    public DogResponseDto(Dog entity) {
        this.id = entity.getId();
        this.member = entity.getMember();
        this.name = entity.getName();
        this.age = entity.getAge();
        this.gender = entity.getGender();
        this.birth = entity.getBirth();
        this.type = entity.getType();
    }
}



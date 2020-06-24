package com.example.demo.dog.vo;

import com.example.demo.member.domain.Member;
import lombok.*;

@Getter
public class DogUpdateRequestDto {
    private Member member;
    private String name;
    private String age;
    private String gender;
    private String birth;
    private String type;

    @Builder
    public DogUpdateRequestDto( Member member, String name, String age, String gender, String birth, String type) {
        this.member = member;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.birth = birth;
        this.type = type;
    }
}

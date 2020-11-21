package com.example.demo.dog.dto;


import com.example.demo.dog.domain.Dog;
import com.example.demo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class DogSaveRequestDto {

    private Long id;
    private Member member;
    private String name;
    private String age;
    private String gender;
    private String birth;
    private String type;

    @Builder
    public DogSaveRequestDto(Member member, String name, String age, String gender, String birth, String type) {
        this.member = member;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.birth = birth;
        this.type = type;
    }

    public Dog toEntity(){
        return Dog.builder()
                .member(member)
                .name(name)
                .age(age)
                .gender(gender)
                .birth(birth)
                .type(type)
                .build();
    }

}

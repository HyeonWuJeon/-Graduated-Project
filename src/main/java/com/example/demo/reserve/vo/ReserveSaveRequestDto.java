package com.example.demo.reserve.vo;

import com.example.demo.hospital.vo.Hospital;
import com.example.demo.member.domain.Member;
import lombok.*;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class ReserveSaveRequestDto implements Serializable {
    private Member member;
    private String date;
    private String description;
    private String name;
    private String address;
    private String tel;
    private String dog;
    private Hospital hospital;
    public void Reserve_Member(Member member){
        this.member = member;
    }

    public void Reserve_Hospital(Hospital hospital){
        this.hospital = hospital;
    }

    @Builder
    public ReserveSaveRequestDto(Member member, String date, String description, String name, String address, String tel, String dog) {
        this.member = member;
        this.date = date;
        this.description = description;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.dog =dog;
    }

    public Reserve toEntity(){
        return Reserve.builder()
                .member(member)
                .name(name)
                .date(date)
                .address(address)
                .tel(tel)
                .description(description)
                .dog(dog)
                .hospital(hospital)
                .build();
    }
}

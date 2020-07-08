package com.example.demo.hospital.vo;

import com.example.demo.member.vo.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class HospitalSaveRequestDto {

    private String name;
    private String address;
    private String tel;
    private Member member;

    @Builder
    public HospitalSaveRequestDto(String name, String address, String tel, Member member) {
        this.name = name;
        this.tel =tel;
        this.address = address;
        this.member = member;
    }

    public Hospital toEntity(){
        return Hospital.builder()
                .name(name)
                .address(address)
                .tel(tel)
                .member(member)
                .build();
    }
}



package com.example.demo.reserve.dto;

import com.example.demo.hospital.domain.Hospital;
import com.example.demo.member.domain.Member;
import com.example.demo.reserve.domain.Reserve;
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
    private Hospital hospital;
    public void Reserve_Member(Member member){
        this.member = member;
    }

    public void Reserve_Hospital(Hospital hospital){
        this.hospital = hospital;
    }

    @Builder
    public ReserveSaveRequestDto(Member member, String date, String description, String name, String address, String tel) {
        this.member = member;
        this.date = date;
        this.description = description;
        this.name = name;
        this.address = address;
        this.tel = tel;
    }

    public Reserve toEntity(){
        return Reserve.builder()
                .member(member)
                .name(name)
                .date(date)
                .address(address)
                .tel(tel)
                .description(description)
                .hospital(hospital)
                .build();
    }
}

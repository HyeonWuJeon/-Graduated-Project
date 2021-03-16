package com.example.demo.reserve.dto;

import com.example.demo.member.domain.Member;
import com.example.demo.reserve.domain.Reserve;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReserveResponseDto {

    private Long id;
    private Member member;
    private String date;
    private String description;
    private String name;
    private String address;
    private String tel;
    private String dog;

    public ReserveResponseDto(Reserve entity) {
        this.id = entity.getId();
        this.member = entity.getMember();
        this.date = entity.getDate();
        this.description = entity.getDescription();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.tel = entity.getTel();
    }
}

package com.example.demo.diagnosis.dto;

import com.example.demo.diagnosis.domain.Air;
import com.example.demo.diagnosis.domain.Corna;
import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.domain.Macak;
import com.example.demo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DiagnosisDto {

    private Long id;
    private Member member;
    private String name; //진단질병명
    private Corna corna;
    private Macak macak;
    private Air air;
    private String dog;

    @Builder
    public DiagnosisDto(Diagnosis entity){
        this.id = entity.getId();
        this.member = entity.getMember();
        this.name = entity.getName();
        this.corna = entity.getCorna();
        this.macak = entity.getMacak();
        this.air = entity.getAir();
        this.dog = entity.getDog();
    }
}

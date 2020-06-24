package com.example.demo.diagnosis.dto;

import lombok.*;

@Getter
public class DiagnosisNameCountDto {

    private String name;
    private Long count;

    public DiagnosisNameCountDto(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
package com.example.demo.diagnosis.dto;

import lombok.Getter;

import java.math.BigInteger;


@Getter
public class DiagnosisNameCountDto {

    private String diseaseTypeName;
    private BigInteger diseaseTypeCount;

    public DiagnosisNameCountDto(String diseaseTypeName, BigInteger diseaseTypeCount) {
        this.diseaseTypeName = diseaseTypeName;
        this.diseaseTypeCount = diseaseTypeCount;
    }
}
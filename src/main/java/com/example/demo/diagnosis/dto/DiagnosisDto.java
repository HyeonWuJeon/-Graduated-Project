package com.example.demo.diagnosis.dto;

import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.dog.domain.Dog;
import com.example.demo.member.domain.Member;
import lombok.Builder;
import lombok.Data;


@Data
public class DiagnosisDto {

    private Long id;
    private String percent; // 진단 질병명
    private String disease_type;
    private Member member;
    private Dog dog;
    private String dogName;

    @Builder
    public DiagnosisDto(Diagnosis entity) {
        this.id = entity.getId();
        this.member = entity.getMember();
        this.dog = entity.getDog();
    }

    @Data
    public  static class Search{
        private String percent; // 진단 질병명
        private String disease_type;
        private Dog dog;

//        @Builder
        public Search(String percent, String disease_type, Dog dog) {
            this.percent = percent;
            this.disease_type = disease_type;
            this.dog = dog;
        }
    }
}

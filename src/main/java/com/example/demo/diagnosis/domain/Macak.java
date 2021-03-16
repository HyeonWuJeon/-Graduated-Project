package com.example.demo.diagnosis.domain;


import com.example.demo.dog.domain.Dog;
import com.example.demo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Macak")
public class Macak extends Diagnosis{

    private String percent;

    protected void setPercent(String percent){
        this.percent = percent;
    }
    public static void Percent(String percent) {
        Macak macak = new Macak();
        macak.setPercent(percent);
    }
    @Builder
    public Macak(String percent, Dog dog, Member member) {
        super(dog, member);
        this.percent =percent;
    }
}

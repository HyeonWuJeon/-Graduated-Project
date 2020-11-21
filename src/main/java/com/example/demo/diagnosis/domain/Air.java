package com.example.demo.diagnosis.domain;

import com.example.demo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("Air")
public class Air extends Diagnosis{

    private String percent;

    protected void setPercent(String percent) {
        this.percent = percent;
    }

    public static void Percent(String percent){
        Air air = new Air();
        air.setPercent(percent);
    }

    @Builder
    public Air(String percent, String dog, Member member) {
        super(dog, member);
        this.percent = percent;
    }
}

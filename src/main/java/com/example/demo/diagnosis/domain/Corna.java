package com.example.demo.diagnosis.domain;


import com.example.demo.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@DiscriminatorValue("Corna")
public class Corna extends Diagnosis {

    private String percent;


    protected Corna() {
        super();
    }
    @Builder
    public Corna(String percent, Member member, String dog)
    {
        super(dog, member);
        this.percent = percent;
    }

}

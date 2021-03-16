package com.example.demo.diagnosis.domain;

import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.dog.domain.Dog;
import com.example.demo.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@SqlResultSetMapping(
        name="ProductOrderedMemberMapping",
        classes = @ConstructorResult(
                targetClass = DiagnosisNameCountDto.class,
                columns = {
                        @ColumnResult(name="diseaseTypeName", type = String.class),
                        @ColumnResult(name="diseaseTypeCount", type = Long.class),
                })
)

@Getter
@Entity
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "disease_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Diagnosis_table")
public class Diagnosis {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="diagnosis_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    private Dog dog;

    @Transient
    private String percent;
    @Transient
    private String disease_type;

    public static Diagnosis setSymptomCause(String symptomCause){
        Diagnosis diagnosis = new Diagnosis();
        return  diagnosis;

    }

    public Diagnosis(Dog dog, Member member) {
        this.dog =dog;
        this.member = member;
    }
//    public Diagnosis(String dog, Member member) {
//        this.dog =dog;
//        this.member = member;
//    }
}

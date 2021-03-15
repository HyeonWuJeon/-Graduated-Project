package com.example.demo.diagnosis.domain;

import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Diagnosis_table")
public class Diagnosis {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    private String name; // 진단 질병명
    private String dog;

//    @Transient
//    private String diseaseTypeName;
//
//    @Transient
//    private String diseaseTypeCount;
//    private String type;
//
//    private String percent;
//
//    @OneToOne
//    private Corna corna;
//    @OneToOne
//    private Macak macak;
//    @OneToOne
//    private Air air;


    public Diagnosis(String dog, Member member) {
        this.dog =dog;
        this.member = member;
//        this.name = name;
//        this.type = type;
    }
}

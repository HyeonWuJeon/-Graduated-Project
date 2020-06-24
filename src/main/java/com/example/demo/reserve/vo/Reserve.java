package com.example.demo.reserve.vo;

import com.example.demo.hospital.vo.Hospital;
import com.example.demo.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Reserve  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String dog;

    @Column(name = "visit_date", nullable = false)
    private String date;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "hospital_name")
    private String name;
    @Column(nullable = false)
    private String tel;

    private String address; //추후 병원에서 가져온다 필요한가 ?

    @Builder
    public Reserve(Long id, Member member, String date, String description, String name, String address , String tel, String dog, Hospital hospital) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.description = description;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.dog = dog;
        this.hospital = hospital;
    }

    public Reserve update(String date, String description) {
        this.date = date;
        this.description = description;
        return this;
    }
}
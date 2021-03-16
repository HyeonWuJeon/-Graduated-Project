package com.example.demo.reserve.domain;

import com.example.demo.hospital.domain.Hospital;
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

    @Column(name = "visit_date", nullable = false)
    private String date;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "hospital_name")
    private String name;
    @Column(nullable = false)
    private String tel;

    private String address;

    @Builder
    public Reserve(Long id, Member member, String date, String description, String name, String address , String tel, Hospital hospital) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.description = description;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.hospital = hospital;
    }

    public Reserve update(String date, String description) {
        this.date = date;
        this.description = description;
        return this;
    }
}
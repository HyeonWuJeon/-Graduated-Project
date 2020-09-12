package com.example.demo.hospital.domain;

import com.example.demo.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hospital {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long id;

    @Column(name = "hospital_name")
    private String name;

    @Column(name = "hospital_tel")
    private String tel;

    @Column(name = "hospital_address")
    @NotBlank
    private String address;

    @OneToOne
    @JoinColumn(name ="member_id")
    private Member member;

    @Builder
    public Hospital(String name, String tel, String address, Member member) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.member = member;
    }
}

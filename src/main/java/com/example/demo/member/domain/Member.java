package com.example.demo.member.domain;


import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.dog.vo.Dog;
import com.example.demo.hospital.vo.Hospital;
import com.example.demo.member.dto.Role;
import com.example.demo.overlap.Address;
import com.example.demo.overlap.BaseTimeEntity;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String password;

    private String birth;

    private String phone;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name ="hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy="member", orphanRemoval = true)
    List<Diagnosis> diList = new ArrayList<>();

    @OneToMany(mappedBy="member", orphanRemoval = true)
    List<Dog> dogList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(Long id, String name, Address address, Role role,String email ,String password, String birth, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.role = role;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.phone = phone;
    }

    public Member update(String city, String street, String zipcode, String phone) {
        this.address = new Address(city, zipcode, street);
        this.phone = phone;
        return this;
    }

    public Member regHospital(Hospital hospital){
        this.hospital = hospital;
        return this;
    }

    public Member deleteHospital(){
        this.hospital = null;
        return this;
    }

    public Member updatePwd(String password) {
        this.password = password;
        return this;
    }

    public Member updateMember(String name, String city, String street, String zipcode, String phone) {
        this.name = name;
        this.address = new Address(city, zipcode, street);
        this.phone = phone;
        return this;
    }

    public Member updateAdmin(String password, String phone) {
        this.password = password;
        this.phone = phone;
        return this;
    }

    public String getRoleKey() {
        return this.role.getValue();
    }
}

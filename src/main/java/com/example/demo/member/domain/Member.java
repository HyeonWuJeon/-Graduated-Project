package com.example.demo.member.domain;


import com.example.demo.config.BaseTimeEntity;
import com.example.demo.config.security.Role;
import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.dog.domain.Dog;
import com.example.demo.hospital.domain.Hospital;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    @Length(max =200)
    @NotBlank
    private String name; //이름

    @Column(length = 100)
    @NotBlank
    private String email; //이메일

    @NotBlank
    @Column(length = 200)
//    @Pattern(regexp = "^[[0-9]$@$!%*#?&]{10,20}$")
    private String password; //비밀번호

    @Length(max = 1)
    @NotBlank
    private String birth; //생년월일

   @Length(max = 11)
    @NotBlank
    private String phone; //휴대폰번호

    @Embedded
    private Address address; //주소

    @OneToOne
    @JoinColumn(name ="hospital_id")
    private Hospital hospital; //병원정보

    @OneToMany(mappedBy="member", orphanRemoval = true)
    List<Diagnosis> diList = new ArrayList<>(); //질병 정보

    @OneToMany(mappedBy="member", orphanRemoval = true)
    List<Dog> dogList = new ArrayList<>(); //반려견정보

    @Enumerated(EnumType.STRING)
    private Role role;          // ADMIN : 관리자 / GUEST : 사용자 / VET : 수의사


    /**
     * Business Logic : 회원가입
     * @param name
     * @param address
     * @param role
     * @param email
     * @param password
     * @param birth
     * @param phone
     */
    @Builder
    public Member(String name, Address address, Role role, String email , String password, String birth, String phone) {
        this.name = name;
        this.address = address;
        this.role = role;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.phone = phone;
    }


    /**
     * Business Logic : 회원 정보 변경
     * @param city
     * @param street
     * @param zipcode
     * @param phone
     * @return
     */
    public Member update(String city, String street, String zipcode, String phone) {
        this.address = new Address(city, zipcode, street);
        this.phone = phone;
        return this;
    }

    /**
     * Business Logic : 병원 등록
     * @param hospital
     * @return
     */
    public Member regHospital(Hospital hospital){
        this.hospital = hospital;
        return this;
    }

    /**
     * Business Logic : 병원 삭제
     * @return
     */
    public Member deleteHospital(){
        this.hospital = null;
        return this;
    }

    /**
     * Business Logic : 패스워드 변경
     * @param password
     * @return
     */
    public Member updatePwd(String password) {
        this.password = password;
        return this;
    }


    /**
     * Business Logic : 관리자 -> 회원정보 수정
     * @param name
     * @param city
     * @param street
     * @param zipcode
     * @param phone
     * @return
     */
    public Member updateMember(String name, String city, String street, String zipcode, String phone) {
        this.name = name;
        this.address = new Address(city, zipcode, street);
        this.phone = phone;
        return this;
    }

//
//    public Member updateAdmin(String password, String phone) {
//        this.password = password;
//        this.phone = phone;
//        return this;
//    }

    /**
     * Business Logic : 권한 수정 사용x
     * @return
     */
    public String getRoleKey() {
        return this.role.getValue();
    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birth='" + birth + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", hospital=" + hospital +
                ", diList=" + diList +
                ", dogList=" + dogList +
                ", role=" + role +
                '}';
    }


}

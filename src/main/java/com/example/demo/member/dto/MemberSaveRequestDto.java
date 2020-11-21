package com.example.demo.member.dto;

import com.example.demo.config.security.Role;
import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter@Setter
public class MemberSaveRequestDto {

    @Length(max = 50)
    @NotBlank
    private String name;
    @Length(max = 50)
    private String email;
    @Length(max = 50)
    private String password;
    private String zipcode;
    private String city;
    private String street;
    private Address address;
    private Role role;
    private String birth;
    @Length(max = 11)
    private String phone;

    /**
     * FUNCTION : 패스워드 암호화
     * @param password
     */
    public void SHA256_PassWord(String password) {
        this.password = password;
    }

    /**
     * FUNCTION : 권한 확인 & 변경
     * @param role
     */
    public void GIVE_Role(Role role) {
        this.role = role;
    }

    /**
     * FUNCTION : 주소정보 저장
     * @param zipcode
     * @param city
     * @param street
     */
    public Address setAddress(String zipcode, String city, String street) {
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
        return new Address(city, zipcode, street);
    }

    /**
     * CONSTRUCTOR : 회원가입 builder 패턴
     */
    @Builder
    public MemberSaveRequestDto(Address address,String name, String email, String password, Role role, String birth, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birth = birth;
        this.phone = phone;
        this.address=address;
    }


    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .address(address)
                .birth(birth)
                .phone(phone)
                .build();
    }
}
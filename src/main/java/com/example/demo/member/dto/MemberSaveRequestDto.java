package com.example.demo.member.dto;

import com.example.demo.member.domain.Member;
import com.example.demo.overlap.Address;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class MemberSaveRequestDto {
    private String name;
    private String email;
    private String password;
    private Address address;
    private Role role;
    private String birth;
    private String phone;


    public void SHA256_PassWord(String password) {
        this.password = password;
    }


    public void GIVE_Role(Role role) {
        this.role = role;
    }



    @Builder
    public MemberSaveRequestDto(String name, String email, String password, Address address, Role role, String birth, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.phone = phone;
        this.birth = birth;
    }


    public Member toEntity(){
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .address(address)
                .role(role)
                .birth(birth)
                .phone(phone)
                .build();
    }
}


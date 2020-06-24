package com.example.demo.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdatePwd {
    private String password;
    private String password2;

    @Builder
    public MemberUpdatePwd(String password, String password2) {
        this.password=password;
        this.password2=password2;

    }
}

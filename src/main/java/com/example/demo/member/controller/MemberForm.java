package com.example.demo.member.controller;
import com.example.demo.config.security.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

        @NotEmpty(message = "이름 입력은 필수입니다")
        private String name;
        @NotEmpty(message = "이메일 입력은 필수입니다")
        private String email;
        @NotEmpty(message = "비밀번호 입력은 필수입니다")
        private String pwd;
        @NotEmpty(message = "비밀번호 확인은 필수입니다")
        private String password;
        @NotEmpty(message = "생일 입력은 필수입니다")
        private String birth;
        @NotEmpty(message = "전화번호 입력은 필수입니다")
        private String phone;
        @NotEmpty(message = "우편번호 입력은 필수입니다")
        private String zipcode;
        @NotEmpty(message = "주소 입력은 필수입니다")
        private String city;
        @NotEmpty(message = "상세주소 입력은 필수입니다")
        private String street;
//        @NotEmpty(message = "구분 입력은 필수입니다")
        private Role role;

}

package com.example.demo.config.security;


import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN","ADMIN"), // 관리자 권한
    GUEST("ROLE_MEMBER","GUEST"), // 고객 권한
    VET("ROLE_VET", "VET"); // 수의사 권한

    private final String value;
    private final String title;
}




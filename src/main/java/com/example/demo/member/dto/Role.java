package com.example.demo.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_MEMBER"),
    VET("ROLE_VET");

    private String value;
}




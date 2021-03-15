package com.example.demo.config.security;

import com.example.demo.member.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        MemberDto.Response member = ((MemberDto.Response) authentication.getPrincipal());
        String token = JwtTokenProvider.createToken(member.getEmail());

        response.addHeader("Authorization",  "BEARER" + " " + token); //LINE :: 토큰을 헤더에 실어 보낸다.

    }
}
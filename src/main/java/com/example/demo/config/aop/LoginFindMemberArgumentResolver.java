package com.example.demo.config.aop;


import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginFindMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isLoginUserAnnotation =parameter.getParameterAnnotation(LoginFindMember.class) != null;
        boolean isUserClass = Member.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return memberService.findMember(user.getUsername());
    }
}

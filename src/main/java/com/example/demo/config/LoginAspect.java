package com.example.demo.config;


import com.example.demo.member.controller.LoginSession;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

@Component
@Aspect
@RequiredArgsConstructor
public class LoginAspect implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;
    Principal principal;

//    public Object loginExecutionSession(Principal principal) throws Throwable{
//        return memberRepository.findEmailCheck(principal.getName());
//
//    }
//
//    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginSession.class) != null;
        boolean isUserClass = MemberResponseDto.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return memberRepository.findEmailCheck(principal.getName());
    }


}

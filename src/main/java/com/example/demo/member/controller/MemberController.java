package com.example.demo.member.controller;

import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import com.example.demo.member.domain.Member;
import com.example.demo.member.dto.MemberResponseDto;
import com.example.demo.member.dto.MemberSaveRequestDto;
import com.example.demo.member.domain.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

import java.util.*;
/**
 * 세션부분 추후 @Aspect 적용하기.
 * */
@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 회원 메인 홈
    @RequestMapping("/")
    public String home(){
        log.info("home logger");

        return "home";
    }

    // 회원가입
    @GetMapping("/member/signup")
    public String createMember(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "member/memberAuth/signup";
    }

    // 회원가입 API
    @PostMapping(value = "/api/member/signup")
    public String createMemberApi(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "member/memberAuth/signUp";
        }

        Address address = new Address(form.getCity(),
                form.getZipcode(),form.getStreet());
        MemberSaveRequestDto member = new MemberSaveRequestDto();

        memberService.SignUp(member.builder()
                .name(form.getName())
                .address(address)
                .birth(form.getBirth())
                .email(form.getEmail())
                .password(form.getPassword())
                .phone(form.getPhone())
                .role(form.getRole())
                .build());

        return "member/memberAuth/signIn";
    }

    //회원정보 리스트
    @GetMapping(value = "/admin/members")
    public String readAllMemberAdmin(Model model) {
        List<MemberResponseDto> members = memberService.findAllDesc();
        model.addAttribute("members", members);

        return "admin/memberControl/memberList";
    }

    @GetMapping("/member/mypage")
    public String readMember(Model model, Principal principal, HttpServletRequest request, HttpSession session) {
        Member member = memberRepository.findEmailCheck(principal.getName());

        if(member != null) {
            model.addAttribute("member", member);
        }

        return "member/memberAuth/myPage";
    }

    // 회원 정보수정 페이지
    @GetMapping("/member/settings/{id}")
    public String updateMember(@PathVariable Long id, Model model) {
        MemberResponseDto dto = memberService.findById(id);

        model.addAttribute("member", dto);

        return "member/memberAuth/settings";
    }

    // 관리자 회원정보 수정페이지
    @GetMapping("/admin/member/settings/{id}")
    public String updateMemberAdmin(@PathVariable Long id, Model model){
        MemberResponseDto dto = memberService.findById(id);

        model.addAttribute("member", dto);

        return "admin/memberControl/settings";
    }

    // 로그인 페이지
    @GetMapping("/member/login")
    public String dispLogin() throws Exception
    {
        return "member/memberAuth/signIn";
    }

    // 회원 로그인 결과
    @GetMapping("/member/login/result")
    public String dispLoginResult()
    {
        return "home";
    }

    // 회원 로그아웃
    @GetMapping("/member/logout/result")
    public String dispLogout()
    {
        return "home";
    }

    // 관리자 정보조회
    @GetMapping("/admin/mypage")
    public String readAdmin(Model model, Principal principal) {
        Member admin = memberRepository.findEmailCheck(principal.getName());

        if(admin != null) {
            model.addAttribute("admin", admin);
        }

        return "admin/adminAuth/admin_myPage";
    }

    // 관리자 정보수정
    @GetMapping("/admin/settings/{id}")
    public String updateAdmin(@PathVariable Long id, Model model) {
        MemberResponseDto adminDto = memberService.findById(id);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        model.addAttribute("admin", adminDto);

        return "admin/adminAuth/admin_settings";
    }
}
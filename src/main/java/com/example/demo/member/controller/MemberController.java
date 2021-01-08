package com.example.demo.member.controller;

import com.example.demo.config.aop.LogExecutionTime;
import com.example.demo.config.aop.LoginFindMember;
import com.example.demo.member.domain.Member;
import com.example.demo.member.dto.MemberResponseDto;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping()
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    // 회원가입
    @GetMapping("/member/signup")
    public String createMember(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "member/memberAuth/signup";
    }

    // 로그인
    @GetMapping("/member/login")
    public String dispLogin() throws Exception
    {
        return "member/memberAuth/signIn";
    }


    // 회원 마이페이지
    @GetMapping("/member/mypage")
    public String readMember(Model model, @LoginFindMember Member member) {
        if(member != null) {
            model.addAttribute("member", member);
        }

        return "member/memberAuth/myPage";
    }

    // 회원 정보수정
    @GetMapping("/member/settings/{id}")
    public String updateMember(@PathVariable Long id, Model model) {
        MemberResponseDto dto = memberService.findById(id);

        model.addAttribute("member", dto);

        return "member/memberAuth/settings";
    }

    // 관리자, 회원 정보 리스트
    @GetMapping(value = "/admin/members")
    @LogExecutionTime
    public String readAllMemberAdmin(Model model) {
        List<MemberResponseDto> members = memberService.findAllDesc();
        model.addAttribute("members", members);

        return "admin/memberControl/memberList";
    }

    // 관리자, 회원 정보수정
    @GetMapping("/admin/member/settings/{id}")
    public String updateMemberAdmin(@PathVariable Long id, Model model){
        MemberResponseDto dto = memberService.findById(id);

        model.addAttribute("member", dto);

        return "admin/memberControl/settings";
    }

    // 관리자 정보조회
    @GetMapping("/admin/mypage")
    public String readAdmin(Model model, @LoginFindMember Member admin) {
        if(admin != null) {
            model.addAttribute("admin", admin);
        }

        return "admin/adminAuth/admin_myPage";
    }

    // 관리자 정보수정
    @GetMapping("/admin/settings/{id}")
    public String updateAdmin(@PathVariable Long id, Model model) {
        MemberResponseDto adminDto = memberService.findById(id);
        model.addAttribute("admin", adminDto);

        return "admin/adminAuth/admin_settings";
    }



}

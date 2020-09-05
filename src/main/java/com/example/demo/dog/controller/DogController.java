package com.example.demo.dog.controller;

import com.example.demo.config.auth.LogExecutionTime;
import com.example.demo.dog.dto.DogResponseDto;
import com.example.demo.dog.dto.DogSaveRequestDto;
import com.example.demo.dog.service.DogService;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;



@Controller
@RequiredArgsConstructor
@Slf4j
public class DogController {

    private final DogService dogService;
    private final MemberService memberService;

    // 사용자 강아지 정보 입력 홈페이지
    @GetMapping("/member/dog/save")
    public String DogcreateForm(Model model) {
        model.addAttribute("flag", true);
        model.addAttribute("dogForm", new DogForm());
        return "member/dogs/dogSignUp";
    }

    // 강아지 정보 저장 API
    @PostMapping(value = "/api/member/dog/save")
    public String Dogcreate(@Valid DogForm form, BindingResult result, Principal principal,Model model) {
        if (result.hasErrors()) {
            return "member/dogs/dogSignUp";
        }

        Member member = memberService.findMember(principal.getName()); //추후 ASPECT 적용대상

        DogSaveRequestDto dog = new DogSaveRequestDto();
        dogService.dog_SignUp(dog.builder()
                .member(member)
                .name(form.getName())
                .type(form.getType())
                .age(form.getAge())
                .birth(form.getBirth())
                .gender(form.getGender())
                .build());

        List<DogResponseDto> Dogs = dogService.findAllDesc(member);

        model.addAttribute("dogs", Dogs);
        return "member/dogs/dogInfo";
    }

    // 사용자 자신의 강아지 정보 조회 홈페이지
    @GetMapping("/member/dogs")
    @LogExecutionTime
    public String DogInfo(Model model, Principal principal) {
        Member member = memberService.findMember(principal.getName());//추후 ASPECT 적용대상
        List<DogResponseDto> Dogs = dogService.findAllDesc(member);

        model.addAttribute("dogs", Dogs);
        return "member/dogs/dogInfo";
    }

    // 강아지 정보 수정 및 삭제 홈페이지
    @GetMapping(value = "/dogs/settings/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        DogResponseDto dto = dogService.findById(id);
        model.addAttribute("dog", dto);

        return "member/dogs/dogModify";
    }

    // 관리자, 회원별 반려견 정보조회
    @GetMapping("/admin/member/{id}/dogs")
    public String adminDogInfo(@PathVariable Long id, Model model) {
        Member member = memberService.findMember(id);


        List<DogResponseDto> dogs = dogService.findAllDesc(member);

        model.addAttribute("member", member);
        model.addAttribute("dogs", dogs);

        return "dog/admin_dogInfo";
    }

    // 관리자, 회원 반려견 정보수정
    @GetMapping("/admin/dogs/settings/{id}")
    public String adminDogSettings(@PathVariable Long id, Model model) {
        DogResponseDto dto = dogService.findById(id);
        model.addAttribute("dog", dto);

        return "dog/admin_dogSettings";
    }

}


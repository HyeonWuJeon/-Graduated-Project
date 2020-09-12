package com.example.demo.member.controller;

import com.example.demo.config.aop.LoginUser;
import com.example.demo.hospital.domain.Hospital;
import com.example.demo.member.dto.MemberSaveRequestDto;
import com.example.demo.member.dto.MemberUpdatePwd;
import com.example.demo.member.dto.MemberUpdateRequestDto;
import com.example.demo.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberApiController  {

    private final FindByIndexNameSessionRepository sessionRepository;
    private final  MemberService memberService;

    // 회원이 직접 정보를 수정하는 API
    @PutMapping("/member/settings/{id}")
    public Long updateForm(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.update(id, requestDto);
    }

    // 회원 패스워드 변경 전용 API
    @PutMapping("/member/settingsPwd/{id}")
    public Long updatePwd(@PathVariable Long id, @RequestBody MemberUpdatePwd requestDto) {
        if(!requestDto.getPassword().equals(requestDto.getPassword2())) {
            throw new IllegalStateException("패스워드 확인 바랍니다.");
        }
        else if(requestDto.getPassword2().isEmpty()){
            throw new IllegalStateException("패스워드 확인 바랍니다.");
        }

        return memberService.updatePwd(id, requestDto);
    }

    // 회원이 직접 정보를 삭제하는 api
    @DeleteMapping("/member/delete/{id}")
    public Long delete(@PathVariable Long id, @LoginUser User user) {
        sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
                user.getUsername()).keySet().forEach(session -> sessionRepository.deleteById((String) session));

        memberService.delete(id); // 회원 정보 삭제 (회원이 만약 병원 관리자라면?)
        return id;
    }

    @GetMapping("/mockTest")
    public String mocktest(){
        return memberService.mockTest();
    }

    // 관리자가 회원정보를 수정하는 API
    @PutMapping("/admin/member/settings/{id}")
    public Long updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.updateMember(id, requestDto);
    }

    // 관리자가 회원정보를 삭제하는 api
    @DeleteMapping("/admin/member/delete/{id}")
    public Long deleteMember(@PathVariable Long id) {
        memberService.delete(id);
        return id;
    }

//    https://woowabros.github.io/experience/2019/01/29/exception-in-transaction.html
//    Rollback Error

    /**
     * CONTROLLER : 회원가입
     * @param form
     * @return
     */
    @PostMapping(value = "/member/signup")
    public String createMemberApi(MemberSaveRequestDto form) {
        return memberService.SignUp(form);
    }


    @PostMapping("/checkEmail")
    public int checkEmail(@RequestBody String user_email) {
        return memberService.validateDuplicateMember(user_email);
    }


    /**
     * ResponseEntity Test
     * @return
     */
    @GetMapping("/Test.htm")
    public ResponseEntity entityTest(){

        Hospital hospital = new Hospital();
        hospital.setTel("011");
        hospital.setAddress("add");
        hospital.setName("현우");

        return new ResponseEntity<>(hospital, HttpStatus.OK);
    }
}
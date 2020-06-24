package com.example.demo.reserve.controller;

import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.reserve.service.ReserveService;
import com.example.demo.reserve.vo.ReserveSaveRequestDto;
import com.example.demo.reserve.vo.ReserveUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
@Slf4j
public class ReserveControllerApi {

    private final ReserveService reserveService;
    private final MemberRepository memberRepository;

    // 사용자 병원 예약 정보 수정 API
    @PutMapping("/api/member/reserve/settings/{id}")
    public Long ReserveUpdateForm(@PathVariable Long id, @RequestBody ReserveUpdateRequestDto requestDto) {

        return reserveService.update(id, requestDto);
    }

    // 관리자 -> 사용자 병원 예약 정보 수정 API
    @PutMapping("/api/admin/reserve/settings/{id}")
    public Long ReserveUpdateFormAdmin(@PathVariable Long id, @RequestBody ReserveUpdateRequestDto requestDto) {

        return reserveService.update(id, requestDto);
    }

    // 사용자 병원 예약 정보 삭제 API
    @DeleteMapping("/api/member/reserve/delete/{id}")
    public Long delete(@PathVariable Long id) {
        reserveService.delete(id);
        return id;
    }

    // 병원 예약 등록 API
    @PostMapping("api/member/reserve")
    public Long reserve(@RequestBody ReserveSaveRequestDto requestDto, Principal principal) {
        Member member = memberRepository.findEmailCheck(principal.getName());
        log.info("강아지확인"+requestDto.getDog());
        return reserveService.Reserve(requestDto, member);


    }
    // 관리자 - > 사용자 병원 예약 정보 삭제 API
    @DeleteMapping("/api/admin/reserve/delete/{id}")
    public Long deleteAdmin(@PathVariable Long id) {

        reserveService.delete(id);
        return id;
    }
}

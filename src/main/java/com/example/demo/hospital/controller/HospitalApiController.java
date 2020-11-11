package com.example.demo.hospital.controller;

import com.example.demo.config.aop.LogExecutionTime;
import com.example.demo.config.aop.LoginFindMember;
import com.example.demo.hospital.domain.Hospital;

import com.example.demo.hospital.dto.HospitalSaveRequestDto;
import com.example.demo.hospital.service.HospitalService;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import com.example.demo.reserve.dto.ReserveUpdateRequestDto;

import com.example.demo.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
public class HospitalApiController {

    private final HospitalService hospitalService;
    private final ReserveService reserveService;

    private final MemberService memberService;

    // 수의사, 동물병원 삭제 API
    @DeleteMapping("/api/vet/hospital/delete/{hospital_id}")
    public Long deleteVetHospital(@PathVariable Long hospital_id, @LoginFindMember Member member) {

        member.deleteHospital();
        hospitalService.deleteHospital(hospital_id);

        return hospital_id;
    }

    // 관리자, 동물병원 삭제 API
    @DeleteMapping("/api/admin/hospital/delete/{hospital_id}")
    public Long deleteAdminHospital(@PathVariable Long hospital_id) {

        Hospital hospital = hospitalService.findHospital(hospital_id);
        Member member = memberService.findMember(hospital.getMember().getId());


        member.deleteHospital();
        hospitalService.deleteHospital(hospital_id);

        return hospital_id;
    }

    // 수의사, 동물병원 예약 수정 API
    @PutMapping("/api/vet/hospital/reservation/{reserve_id}")
    public Long updateVetHospitalReserve(@PathVariable Long reserve_id, @RequestBody ReserveUpdateRequestDto requestDto) {
        return reserveService.update(reserve_id, requestDto);
    }

    // 병원등록 API
    @PostMapping(value = "/api/vet/hospital/register")
    public Long createHospital(@RequestBody HospitalSaveRequestDto Dto, @LoginFindMember Member member) {

        if(member.getHospital() != null){
            throw new IllegalStateException("병원등록은 하나만 됩니다.");
        }

        HospitalSaveRequestDto hospital = new HospitalSaveRequestDto();

        Long id = hospitalService.reg(hospital.builder()
                .name(Dto.getName())
                .address(Dto.getAddress())
                .tel(Dto.getTel())
                .member(member)
                .build(), member);

        return id;
    }
    // 수의사, 동물병원 예약 삭제 API
    @DeleteMapping("/api/vet/hospital/reservation/delete/{id}")
    public Long delete(@PathVariable Long id) {
        reserveService.delete(id);

        return id;
    }

    private final AtomicInteger atomicInteger = new AtomicInteger();

    @GetMapping("/3second")
    @LogExecutionTime
    public String threeSecond() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(); //?
        Thread.sleep(3000);
        return "success - " + this.atomicInteger.incrementAndGet();
    }
}

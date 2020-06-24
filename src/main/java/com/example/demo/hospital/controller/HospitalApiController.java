package com.example.demo.hospital.controller;

import com.example.demo.hospital.repository.HospitalRepository;
import com.example.demo.hospital.service.HospitalService;
import com.example.demo.hospital.vo.Hospital;
import com.example.demo.hospital.vo.HospitalSaveRequestDto;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.reserve.service.ReserveService;
import com.example.demo.reserve.vo.ReserveUpdateRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class HospitalApiController {

    private final HospitalService hospitalService;
    private final ReserveService reserveService;
    private final HospitalRepository hospitalRepository;
    private final MemberRepository memberRepository;


    // 수의사, 동물병원 삭제 API
    @DeleteMapping("/api/vet/hospital/delete/{hospital_id}")
    public Long deleteVetHospital(@PathVariable Long hospital_id, Principal principal) {

        Member member = memberRepository.findEmailCheck(principal.getName());

        member.deleteHospital();

        hospitalService.deleteHospital(hospital_id);

        return hospital_id;
    }

    // 관리자, 동물병원 삭제 API
    @DeleteMapping("/api/admin/hospital/delete/{hospital_id}")
    public Long deleteAdminHospital(@PathVariable Long hospital_id) {
        Hospital hospital = hospitalRepository.findOne(hospital_id);

        Member member = memberRepository.findOne(hospital.getMember().getId());
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
    public Long createHospital(@RequestBody HospitalSaveRequestDto Dto, Principal principal) {

        Member member = memberRepository.findEmailCheck(principal.getName());

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
}

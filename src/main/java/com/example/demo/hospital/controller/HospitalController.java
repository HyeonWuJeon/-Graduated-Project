package com.example.demo.hospital.controller;

import com.example.demo.config.aop.LogExecutionTime;
import com.example.demo.config.aop.LoginFindMember;
import com.example.demo.hospital.domain.Hospital;
import com.example.demo.hospital.dto.HospitalResponseDto;
import com.example.demo.hospital.service.HospitalService;
import com.example.demo.member.domain.Member;
import com.example.demo.reserve.dto.ReserveResponseDto;
import com.example.demo.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HospitalController {

    private final HospitalService hospitalService;
    private final ReserveService reserveService;
    // 동물병원 등록 페이지
    @GetMapping("/vet/hospital/registration")
    public String registerHospital(Model model) {
        model.addAttribute("hospitalForm", new HospitalForm());

        return "hospital/registration";
    }

    // 관리자, 전체 동물병원 조회
    @GetMapping(value = "/admin/hospitalList")
    @LogExecutionTime
    public String allHospital(Model model) {
        List<HospitalResponseDto> Dto = hospitalService.findAllDesc();

        model.addAttribute("hospital", Dto);

        return "admin/hospital/hospitalList";
    }

    // 관리자, 동물병원 조회
    @GetMapping("/member/hospital/settings/{id}")
    @LogExecutionTime
    public String updateForm(@PathVariable Long id, Model model) {
        HospitalResponseDto dto = hospitalService.findById(id);
        model.addAttribute("hos", dto);

        return "admin/hospital/hospitalDetail";
    }

    // 수의사, 동물병원 조회
    @GetMapping("/vet/myHospital")
    @LogExecutionTime
    public String readMyHospital(Model model, @LoginFindMember Member member) {

        if(member.getHospital() == null) {
            return "hospital/registration";      // 동물병원 등록 페이지 redirect
        }

        HospitalResponseDto hospitalDto = hospitalService.findById(member.getHospital().getId());

        model.addAttribute("myHospital", hospitalDto);

        return "hospital/myHospital";
    }

    // 수의사, 동물병원 예약 조회
    @GetMapping("/vet/hospital/reservationList")
    @LogExecutionTime
    public String readMyReservation(Model model, @LoginFindMember Member member) {

        Hospital hospital =  hospitalService.findHospital(member.getHospital().getId());
        List<ReserveResponseDto> Reserves = reserveService.findAllHospital(hospital);

        model.addAttribute("reserves", Reserves);

        return "hospital/reservationList";
    }

    // 수의사, 동물병원 예약 수정
    @GetMapping("/vet/hospital/reservation/{id}")
    public String updateEachReservation(@PathVariable Long id, Model model) {
        ReserveResponseDto reserveDto = reserveService.findById(id);
        model.addAttribute("reserves", reserveDto);
        return "hospital/reservationSettings";
    }
}
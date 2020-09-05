package com.example.demo.reserve.service;

import com.example.demo.hospital.domain.Hospital;
import com.example.demo.member.domain.Member;

import com.example.demo.hospital.service.HospitalService;
import com.example.demo.reserve.repository.ReserveRepository;
import com.example.demo.reserve.domain.Reserve;
import com.example.demo.reserve.dto.ReserveResponseDto;
import com.example.demo.reserve.dto.ReserveSaveRequestDto;
import com.example.demo.reserve.dto.ReserveUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;

    private final HospitalService hospitalService;

    // 사용자가 본인의 병원예약정보조회
    @Transactional(readOnly = true)
    public List<ReserveResponseDto> findAllDesc(Member member) {
        return reserveRepository.findAllMemberDesc(member).stream()
                .map(ReserveResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(ReserveSaveRequestDto Dto) {
        return reserveRepository.save(Dto.toEntity()).getId();
    }

    // 관리자가 모든 예약정보 조회
    @Transactional(readOnly = true)
    public List<ReserveResponseDto> findAll() {
        return reserveRepository.findAll().stream()
                .map(ReserveResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReserveResponseDto> findAllHospital(Hospital hospital) {
        return reserveRepository.findAllHospitalDesc(hospital).stream()
                .map(ReserveResponseDto::new)
                .collect(Collectors.toList());
    }

    // 사용자가 본인의 병원예약정보수정 홈페이지 GET
    @Transactional(readOnly = true)
    public ReserveResponseDto findById(Long id) {
        Reserve entity = reserveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 없습니다. id=" + id));

        return new ReserveResponseDto(entity);
    }

    // 사용자가 본인의 병원예약정보수정 PUT
    @Transactional
    public Long update(Long id, ReserveUpdateRequestDto requestDto) {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 없습니다. id=" + id));

        reserve.update(requestDto.getDate(), requestDto.getDescription());

        return id;
    }

    // 사용자가 본인의 병원예약정보삭제 DELETE
    @Transactional
    public void delete(Long id) {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 없습니다. id=" + id));
        reserveRepository.delete(reserve);
    }


    // 예약테이블 FK 삭제
    @Transactional
    public void delete_member(Member member) {
        List<Reserve> reserve = reserveRepository.findAllMemberDesc(member); // 사용자일 경우

        if(reserve != null) {
            for (Reserve re : reserve) {
                reserveRepository.delete(re);
            }
        }
    }

    // 사용자 병원예약 POST
    @Transactional
    public Long Reserve(ReserveSaveRequestDto reserveDto,Member member) {
        Hospital hospital =  hospitalService.findHospital(reserveDto.getName());
        reserveDto.Reserve_Hospital(hospital);
        reserveDto.Reserve_Member(member);

        return reserveRepository.save(reserveDto.toEntity()).getId();
    }
}

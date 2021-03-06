package com.example.demo.hospital.service;

import com.example.demo.hospital.domain.Hospital;
import com.example.demo.hospital.dto.HospitalResponseDto;
import com.example.demo.hospital.dto.HospitalSaveRequestDto;
import com.example.demo.hospital.repository.HospitalRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.reserve.domain.Reserve;
import com.example.demo.reserve.repository.ReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final ReserveRepository reserveRepository;

    // 관리자, 전체 동물병원 조회
    @Transactional(readOnly = true)
    public List<HospitalResponseDto> findAllDesc() {
        return hospitalRepository.findAllDesc().stream()
                .map(HospitalResponseDto::new)
                .collect(Collectors.toList());
    }

    //개인 동물병원 조회
    @Transactional(readOnly = true)
    public Hospital findHospital(Object id) {


        if(id instanceof Long ){
            return hospitalRepository.findById((Long) id)
                    .orElseThrow(()->new IllegalArgumentException("동물병원이 없습니다 = " + id));
        }
        else {
            return hospitalRepository.findHospital((String) id)
                    .orElseThrow(()-> new IllegalArgumentException("동물병원이 없습니다 = "+ id));
        }
    }



    // 수의사, 동물병원 등록
    @Transactional
    public Long reg(HospitalSaveRequestDto hosDto, Member member) {
        Hospital hospital = hospitalRepository.save(hosDto.toEntity());
        member.regHospital(hospital);

        return hospital.getId();
    }

    // 수의사, 동물병원 삭제
    @Transactional
    public void deleteHospital(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 동물병원이 이미 삭제되어 있습니다. id=" +id));
        List<Reserve> reserves = reserveRepository.findAllHospitalDesc(hospital);

        for(Reserve re : reserves){
            reserveRepository.delete(re);
        }
        hospitalRepository.delete(hospital); //병원 delete쿼리
    }

    public HospitalResponseDto findById(Long id) {
        Hospital entity = hospitalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 동물병원이 없습니다. id=" + id));

        return new HospitalResponseDto(entity);
    }
}

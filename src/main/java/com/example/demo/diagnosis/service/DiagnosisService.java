package com.example.demo.diagnosis.service;

import com.example.demo.diagnosis.domain.Air;
import com.example.demo.diagnosis.domain.Corna;
import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.domain.Macak;
import com.example.demo.diagnosis.repository.AirRepository;
import com.example.demo.diagnosis.repository.CornaRepository;
import com.example.demo.diagnosis.repository.DiagnosisRepository;
import com.example.demo.diagnosis.repository.MacakRepository;
import com.example.demo.diagnosis.dto.DiagnosisDto;
import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final MacakRepository macakRepository;
    private final CornaRepository cornaRepository;
    private final AirRepository airRepository;

    @Transactional
    public void DiagnosisSetting(String data, String cor, String ma, String ar, String dog, Member member) {

        int dataLen = data.length();
        String name = data.substring(1, dataLen-1);

        Corna corna = Corna.builder()
                .percent(cor)
                .build();

        Macak macak = Macak.builder()
                .percent(ma)
                .build();

        Air air = Air.builder()
                .percent(ar)
                .build();

        Diagnosis re = new Diagnosis();

        cornaRepository.save(corna);
        macakRepository.save(macak);
        airRepository.save(air);

        re.setAir(air);
        re.setCorna(corna);
        re.setMacak(macak);
        re.setMember(member);
        re.setName(name);
        re.setDog(dog);
        diagnosisRepository.save(re);
    }

    @Transactional(readOnly = true)
    public DiagnosisDto findById(Long id){
            Diagnosis entity = diagnosisRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자 or 관리자가 없습니다. id=" + id));

            return new DiagnosisDto(entity);
    }

    @Transactional(readOnly = true)
    public List<DiagnosisDto> findAllDesc(Member member) {
        return diagnosisRepository.findAllDesc(member).stream()
                .map(DiagnosisDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DiagnosisNameCountDto> findNameCount() {
        List<DiagnosisNameCountDto> diagName = diagnosisRepository.findNameCount();
        return diagName;
    }

    //삭제 api
    @Transactional
    public void delete (List<Diagnosis> diagnosis) {

        for(Diagnosis d : diagnosis){
            diagnosisRepository.delete(d);
            cornaRepository.delete(d.getCorna());
            macakRepository.delete(d.getMacak());
            airRepository.delete(d.getAir());
        }
    }
}

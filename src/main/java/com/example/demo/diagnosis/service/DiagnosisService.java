package com.example.demo.diagnosis.service;

import com.example.demo.diagnosis.domain.Air;
import com.example.demo.diagnosis.domain.Corna;
import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.domain.Macak;
import com.example.demo.diagnosis.dto.DiagnosisDto;
import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.diagnosis.repository.DiagnosisRepository;
import com.example.demo.dog.domain.Dog;
import com.example.demo.dog.repository.DogRepository;
import com.example.demo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    private final DogRepository dogRepository;

    @PersistenceContext
    EntityManager em;


    @Transactional
    public void DiagnosisSetting(String symptomCause, String cor, String ma, String ar, Long dogId, Member member) {
        Dog dog = dogRepository.findById(dogId).get();

        dog.symptomCauseUpdate(symptomCause);//증상 업데이트
//        diagnosisRepository.save(diagnosis);

//        diagnosisRepository.save()
        diagnosisRepository.save(Corna.builder()
                .percent(cor).member(member).dog(dog).build());
        diagnosisRepository.save(Macak.builder()
                .percent(ma).member(member).dog(dog).build());
        diagnosisRepository.save(Air.builder()
                .percent(ar).member(member).dog(dog).build());
    }

    @Transactional(readOnly = true)
    public DiagnosisDto findById(Long id) {
        Diagnosis entity = diagnosisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자 or 관리자가 없습니다. id=" + id));

        return new DiagnosisDto(entity);
    }


    @Transactional(readOnly = true)
    public List<Diagnosis> findAllDesc(Member member) {

        return diagnosisRepository.findAllDesc(member.getId());
    }

    // 질병, 종 통계
    @Transactional
    public List<DiagnosisNameCountDto> findNameCount() {
        JpaResultMapper result = new JpaResultMapper();
        String sql = "SELECT symptom_cause, COUNT(symptom_cause) as cnt FROM dog GROUP BY symptom_cause";
        Query query =  em.createNativeQuery(sql);
        List<DiagnosisNameCountDto> diagName = result.list(query, DiagnosisNameCountDto.class);

        return diagName;
    }

    // 삭제
    @Transactional
    public void delete(List<Diagnosis> diagnosis) {
        for (Diagnosis diagno : diagnosis) {
            diagnosisRepository.delete(diagno);
        }
    }
}

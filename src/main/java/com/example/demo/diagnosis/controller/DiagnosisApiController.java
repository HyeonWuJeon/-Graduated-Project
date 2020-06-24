package com.example.demo.diagnosis.controller;

import com.example.demo.diagnosis.service.DiagnosisService;
import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.disease.dto.DiseaseResponseDto;
import com.example.demo.disease.service.DiseaseService;
import com.example.demo.dog.service.DogService;
import com.example.demo.dog.vo.DogTypeCountDto;
import com.example.demo.symptom.repository.SymptomRepository;
import com.example.demo.symptom.vo.Symptom;
import com.example.demo.symptom.vo.SymptomForm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DiagnosisApiController {

    private final SymptomRepository symptomRepository;
    private final DiseaseService diseaseService;
    private final DiagnosisService diagnosisService;
    private final DogService dogService;

    // AI 모델 변경
    @PostMapping("/api/AIRemodeling")
    public String variable(@Valid SymptomForm form, Model model) {

        log.info("값 확인");
        log.info("증상" + form.getModel());
        log.info("test_size" + form.getTest_size());
        log.info(form.getRandom_state());

        symptomRepository.save(Symptom.builder()
                .name(form.getModel())
                .build());

        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://192.168.43.33:80/reset";
        String url = "http://localhost:80/reset";

        MultiValueMap<String,String> parameters = new LinkedMultiValueMap<String,String>();

        parameters.add("add", form.getModel());
        parameters.add("random_state", form.getRandom_state());
        parameters.add("test_size", form.getTest_size());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,parameters,String.class);

        List<DiseaseResponseDto> diseasesAll = diseaseService.findAllDesc();
        List<DiagnosisNameCountDto> diagnosisNames = diagnosisService.findNameCount();
        List<DogTypeCountDto> dogCouts = dogService.findDogCount();

        model.addAttribute("dis", diseasesAll);
        model.addAttribute("diagName", diagnosisNames);
        model.addAttribute("symptomForm", new SymptomForm());
        model.addAttribute("dogCount", dogCouts);

        return "disease/diseaseInfo";
    }
}

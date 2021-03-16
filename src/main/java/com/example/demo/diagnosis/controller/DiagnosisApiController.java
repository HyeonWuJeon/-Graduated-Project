package com.example.demo.diagnosis.controller;

import com.example.demo.symptom.domain.Symptom;
import com.example.demo.symptom.dto.SymptomForm;
import com.example.demo.symptom.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiagnosisApiController {
    private final SymptomRepository symptomRepository;

    /**
     * FUNCTION :: AI 모델 변경
     *
     * @param form
     * @param
     * @return
     */
    @PostMapping("/api/AIRemodeling")
    public void variable(@Valid SymptomForm form) {

        //LINE :: 증상을 추가한다.
        System.out.println("들어오니?...");
        symptomRepository.save(Symptom.builder()
                .name(form.getModel())
                .build());

        RestTemplate restTemplate = new RestTemplate();

        //LINE :: FLASK 서버와 연결
//        String url = "http://15.165.169.119:5000/reset";
        String url = "http://localhost:80/reset";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

        parameters.add("add", form.getModel()); // LINE :: 증상
        parameters.add("random_state", form.getRandom_state()); // LINE :: 가지수
        parameters.add("test_size", form.getTest_size()); // LINE :: 테스트 사이즈

        WebClient webClient = WebClient.create();
//
//        webClient.mutate()
//                .baseUrl("http://localhost:80/")
//                .build().post()
//                .uri("/reset")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .bodyValue(parameters)
//                .retrieve()
//                .bodyToMono(String.class);


        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, parameters, String.class); // LINE :: 파라메타 전송

    }
}
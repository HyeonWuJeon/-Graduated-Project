package com.example.demo.disease.controller;

import com.example.demo.config.aop.LogExecutionTime;
import com.example.demo.config.aop.LoginFindMember;
import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.dto.DiagnosisDto;
import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.diagnosis.service.DiagnosisService;
import com.example.demo.disease.dto.DiseaseResponseDto;
import com.example.demo.disease.service.DiseaseService;
import com.example.demo.dog.domain.Dog;
import com.example.demo.dog.dto.DogResponseDto;
import com.example.demo.dog.dto.DogTypeCountDto;
import com.example.demo.dog.service.DogService;
import com.example.demo.hospital.dto.HospitalResponseDto;
import com.example.demo.hospital.service.HospitalService;
import com.example.demo.member.domain.Member;
import com.example.demo.symptom.dto.SymptomForm;
import com.example.demo.symptom.dto.SymptomResponseDto;

import com.example.demo.symptom.service.SymptomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class DiseaseController {

    private final DiseaseService diseaseService;
    private final DogService dogService;
    private final DiagnosisService diagnosisService;
    private final SymptomService symptomService;
    private final HospitalService hospitalService;

    // 전체 질병 정보 시각화
    @GetMapping("/admin/diseases")
    @LogExecutionTime
    public String DiseaseInfoPage(Model model) {
        List<DiagnosisNameCountDto> diagnosisNames = diagnosisService.findNameCount();
        List<DogTypeCountDto> dogCounts = dogService.findDogCount();

        model.addAttribute("diagName", diagnosisNames);
        model.addAttribute("symptomForm", new SymptomForm());
        model.addAttribute("dogCount", dogCounts);

        return "disease/diseaseInfo";
    }

    // 질병 진단 문진표
    @GetMapping("/member/disease/chart")
    @LogExecutionTime
    public String DiseaseForm(Model model, @LoginFindMember Member member) {
        List<DogResponseDto> Dogs = dogService.findAllDesc(member);
        List<SymptomResponseDto> Symptoms = symptomService.findAllDesc();

        model.addAttribute("dogs", Dogs);
        model.addAttribute("DiseaseForm", new DiseaseForm());
        model.addAttribute("spt", Symptoms);

        return "disease/diseaseChart";
    }

    // 외부 API와 연동
    @PostMapping("/api/disease/form")
    @LogExecutionTime
    public String callAPI_put(DiseaseForm form, Model model, @LoginFindMember Member member) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        WebClient webClient = WebClient.create();

//        String url = "http://15.165.169.119:5000/test";
        String url = "http://localhost:80/test";



        MultiValueMap<String,String> parameters = new LinkedMultiValueMap<String,String>();
        for(int i = 0; i < form.getSymptom().size(); i++) {
            parameters.add("증상" + i, form.getSymptom().get(i));
        }

//        webClient.mutate()
//                .baseUrl("http://localhost:80/")
//                .build().post()
//                .uri("/test")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .bodyValue(parameters)
//                .retrieve()
//                .bodyToMono(String.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,parameters,String.class);

        HashMap<String, Object> result = new HashMap<String, Object>();

        String jsonInString = "";
        JsonParser parser = new JsonParser();

        // Flask에서 예측 값 받아오기
        try {
            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header); // 값 받기

//            String url2 = "http://15.165.169.119:5000/test"; ec2
            String url2 = "http://localhost:80/test"; // local

            ResponseEntity<Object> resultMap = restTemplate.exchange(url2, HttpMethod.POST,entity, Object.class);


            result.put("Statuscode", resultMap.getStatusCodeValue());
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody());

            ObjectMapper mapper = new ObjectMapper();

            jsonInString = mapper.writeValueAsString(resultMap.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body", e.getStatusText());
        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body", "excpetion 오류");
        }

        Object obj = parser.parse(jsonInString);
        JsonObject jsonObj = (JsonObject) obj;

        // setting
        diagnosisService.DiagnosisSetting(jsonObj.get("data").toString(), jsonObj.get("코로나 바이러스").toString(),
                jsonObj.get("마카다미아너트 중독증").toString(), jsonObj.get("기관지 확장증").toString(), form.getId(), member);

        List<DiseaseResponseDto> diseaseAll = diseaseService.findAllDesc();
        List<HospitalResponseDto> hospitalList = hospitalService.findAllDesc();

        if(member != null) {
            model.addAttribute("member", member);
            model.addAttribute("diagnosis", jsonObj.get("data")); // 가장 확률이 높은 질병
            model.addAttribute("macak", jsonObj.get("마카다미아너트 중독증"));
            model.addAttribute("corna", jsonObj.get("코로나 바이러스"));
            model.addAttribute("bronchus", jsonObj.get("기관지 확장증"));
            model.addAttribute("diseases", diseaseAll);
            model.addAttribute("forms", form);
            model.addAttribute("hosList", hospitalList);
        }
        return "member/recommends/recommendation";

    }

    // 회원이 보는 진단기록리스트
    @GetMapping(value = "/member/chart/record")
    @LogExecutionTime
    public String list(Model model, @LoginFindMember Member member) {
        List<Diagnosis> diagnosis = diagnosisService.findAllDesc(member);
        model.addAttribute("dias", diagnosis);
        return "diagnosis/diagnosisList";
    }

    // 진단 시각화 페이지
    @GetMapping("/member/chart/record/{id}")
    @LogExecutionTime
    public String updateForm(@PathVariable Long id, Model model) {
        DiagnosisDto diagnosisInfo = diagnosisService.findById(id);

        model.addAttribute("diagInfo", diagnosisInfo);

        return "diagnosis/diagnosisInfo";
    }
}
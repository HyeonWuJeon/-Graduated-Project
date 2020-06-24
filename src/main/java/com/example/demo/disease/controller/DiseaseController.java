package com.example.demo.disease.controller;

import com.example.demo.diagnosis.domain.Diagnosis;
import com.example.demo.diagnosis.service.DiagnosisService;
import com.example.demo.diagnosis.dto.DiagnosisDto;
import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.disease.dto.DiseaseResponseDto;
import com.example.demo.disease.service.DiseaseService;
import com.example.demo.dog.service.DogService;
import com.example.demo.dog.vo.DogResponseDto;
import com.example.demo.dog.vo.DogTypeCountDto;
import com.example.demo.hospital.service.HospitalService;
import com.example.demo.hospital.vo.HospitalResponseDto;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.symptom.service.SymptomService;
import com.example.demo.symptom.vo.SymptomForm;
import com.example.demo.symptom.vo.SymptomResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Controller
public class DiseaseController {

    private final MemberRepository memberRepository;

    private final DiseaseService diseaseService;
    private final DogService dogService;
    private final DiagnosisService diagnosisService;
    private final SymptomService symptomService;
    private final HospitalService hospitalService;

    // 전체 질병 정보 시각화
    @GetMapping("/admin/diseases")
    public String DiseaseInfoPage(Model model) {

        List<DiseaseResponseDto> diseasesAll = diseaseService.findAllDesc();
        List<DiagnosisNameCountDto> diagnosisNames = diagnosisService.findNameCount();
        List<DogTypeCountDto> dogCounts = dogService.findDogCount();

        model.addAttribute("dis", diseasesAll);
        model.addAttribute("diagName", diagnosisNames);
        model.addAttribute("symptomForm", new SymptomForm());
        model.addAttribute("dogCount", dogCounts);

        return "disease/diseaseInfo";
    }

    // 질병 진단 문진표
    @GetMapping("/member/disease/chart")
    public String DiseaseForm(Model model, Principal principal) {
        Member member = memberRepository.findEmailCheck(principal.getName());//추후 ASPECT 적용대상
        List<DogResponseDto> Dogs = dogService.findAllDesc(member);
        List<SymptomResponseDto> Symptoms = symptomService.findAllDesc();

        model.addAttribute("dogs", Dogs);
        model.addAttribute("DiseaseForm", new DiseaseForm());
        model.addAttribute("spt", Symptoms);

        return "disease/diseaseChart";
    }

    // 외부 API와 연동
    @PostMapping("/api/disease/form")
    public String callAPI_put(@Valid DiseaseForm form, Model model, Principal principal) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://192.168.43.33:80/test";
        String url = "http://localhost:80/test";

        MultiValueMap<String,String> parameters = new LinkedMultiValueMap<String,String>();
        Diagnosis diagnosis = new Diagnosis();

        for(int i = 0; i < form.getSymptom().size(); i++) {
            parameters.add("증상" + i, form.getSymptom().get(i));
        }

        // 플라스크에 증상 값을 POST 매핑으로 던져준다.
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,parameters,String.class);

        HashMap<String, Object> result = new HashMap<String, Object>();

        String jsonInString = "";
        JsonParser parser = new JsonParser();

        // FLASK에서 예측 값 받아오기
        try {
            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header); // 값 받기

//            String url2 = "http://192.168.43.33:80/test";
            String url2 = "http://localhost:80/test";
            ResponseEntity<Object> resultMap = restTemplate.exchange(url2, HttpMethod.POST,entity, Object.class);

            result.put("Statuscode", resultMap.getStatusCodeValue());
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody());

            ObjectMapper mapper = new ObjectMapper();

            jsonInString = mapper.writeValueAsString(resultMap.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            result.put("body", e.getStatusText());
            System.out.println("dfdfdfdf");
            System.out.println(e.toString());
        } catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body", "excpetion 오류");
            System.out.println(e.toString());
        }

        Object obj = parser.parse(jsonInString);
        JsonObject jsonObj = (JsonObject) obj;
        JsonElement k = jsonObj.get("코로나 바이러스");

        Member member = memberRepository.findEmailCheck(principal.getName());

        // setting
        diagnosisService.DiagnosisSetting(jsonObj.get("data").toString(), jsonObj.get("코로나 바이러스").toString(),
                jsonObj.get("마카다미아너트 중독증").toString(), jsonObj.get("기관지 확장증").toString(), form.getChoice(), member);

        List<DiseaseResponseDto> diseaseAll = diseaseService.findAllDesc();
        List<HospitalResponseDto> hospitalList = hospitalService.findAllDesc();

        if(member != null) {

            model.addAttribute("member", member);
            model.addAttribute("diagnosis", jsonObj.get("data"));
            model.addAttribute("macak", jsonObj.get("마카다미아너트 중독증"));
            model.addAttribute("corna", jsonObj.get("코로나 바이러스"));
            model.addAttribute("bronchus", jsonObj.get("기관지 확장증"));
            model.addAttribute("diseases", diseaseAll);
            model.addAttribute("forms", form);
            model.addAttribute("hosList", hospitalList);
    }

        return "members/recommends/recommendation";
    }

    // 회원이 보는 진단기록리스트
    @GetMapping(value = "/member/chart/record")
    public String list(Model model, Principal principal) {
        Member member = memberRepository.findEmailCheck(principal.getName()); // 추후 ASPECT 적용대상
        List<DiagnosisDto> diagnosis = diagnosisService.findAllDesc(member);
        model.addAttribute("dias", diagnosis);

        return "diagnosis/diagnosisList";
    }

    // 진단 시각화 페이지
    @GetMapping("/member/chart/record/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        DiagnosisDto diagnosisInfo = diagnosisService.findById(id);

        log.info(diagnosisInfo.getId().toString());
        log.info(diagnosisInfo.getDog());
        log.info(diagnosisInfo.getMacak().getPercent());
        log.info(diagnosisInfo.getCorna().getPercent());
        log.info(diagnosisInfo.getAir().getPercent());

        model.addAttribute("diagInfo", diagnosisInfo);

        return "diagnosis/diagnosisInfo";
    }
}

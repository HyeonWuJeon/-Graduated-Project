package com.example.demo.APITest;

import com.example.demo.diagnosis.dto.DiagnosisNameCountDto;
import com.example.demo.diagnosis.repository.DiagnosisRepository;
import com.example.demo.diagnosis.service.DiagnosisService;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@Transactional
public class DiagnosisTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DiagnosisService diagnosisService;

    @Autowired
    DiagnosisRepository diagnosisRepository;
    @Autowired
    MemberService memberService;
    @PersistenceContext
    EntityManager em;


//    @Before
//    public void Insert() {
//        Address address = new Address("경기도", "광명시", "어딘가");
//
//        memberService.SignUp(MemberSaveRequestDto.builder()
//                .name("현우")
//                .address(address)
//                .birth("1995-11-29")
//                .email("admin@mail.com")
//                .password("12345")
//                .phone("010-111-222")
//                .role(Role.GUEST)
//                .build());
//    }
    @Test
    public void hello() throws Exception {
        Member member = em.find(Member.class, 1L);
        diagnosisService.DiagnosisSetting("코로나 바이러스", "30","60","10", "다은이",member);

    }

    @Test
    public void countUnitTest() throws Exception{

        List<DiagnosisNameCountDto> diag = new ArrayList<>();
//        DiagnosisNameCountDto a = DiagnosisNameCountDto("name")
        List<DiagnosisNameCountDto> diags = diagnosisRepository.countByName();

        System.out.println("diags.toString() = " + diags.toString());
    }
}

//package com.example.demo.APITest;
//
//import com.example.demo.member.repository.MemberRepository;
//import com.example.demo.member.vo.*;
//import com.example.demo.member.service.MemberService;
//import com.example.demo.overlap.Address;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//
//
//import javax.persistence.EntityManager;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//
//@RunWith(SpringRunner.class)
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
//@Transactional
////@WebMvcTest
//public class MemberServiceApiTest {
//
//
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    EntityManager em;
//
////    @LocalServerPort
////    private int port;
//
////    @Autowired
////    private TestRestTemplate restTemplate;
////
////
////    @Autowired
////    private WebApplicationContext context;
//
////    private MockMvc mvc;
//
//
//    /**
//     * 중복 확인
//     */
//
//    @Test
//    public void 회원가입() throws Exception {
//        //given
//        Member member = new Member();
//        MemberSaveRequestDto memberSaveRequestDto = new MemberSaveRequestDto();
//        Address address = new Address("경기도", "광명시", "어딘가");
//
//        member.builder()
//                .name("현우")
//                .address(address)
//                .birth("1995-11-29")
//                .email("gachon@mail.com")
//                .password("12345")
//                .phone("010-111-222")
//                .role(Role.GUEST)
//                .build();
//        //when
//
//        Member TestMember = memberRepository.save(member);
//
//        Member T = em.find(Member.class, 1L);
//        //then
//        assertEquals(T.getId(), TestMember.getId());
//    }
//
//    @Test(expected = IllegalStateException.class)
//    public void 중복회원가입() throws Exception {
//        //given
//        MemberSaveRequestDto member = new MemberSaveRequestDto();
//
//        //given + when
//        memberService.SignUp(member.builder()
//                .email("test@gmail.com")
//                .build());
//
//
////        em.flush();
//        //then
//        fail("예외가 발생해야 한다");
//    }
//
//
////    @Test
////    @Rollback(false)
////    public void 회원수정() throws Exception {
////
////        Member member = memberRepository.findOne(10L);
////
////        Address address = new Address("abc", "kfc", "def");
////        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
////                .password("12345")
////                .build();
////        memberService.update(10L, requestDto);
////
////        String url = "http://localhost:" + port + "/api/member/settings/10";
////
////    }
//}

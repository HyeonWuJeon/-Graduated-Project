package com.example.demo.APITest;

import com.example.demo.config.security.Role;
import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import com.example.demo.member.dto.MemberSaveRequestDto;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
//@Transactional
public class MemberServiceApiTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mvc;

    @PersistenceContext
    EntityManager em;


    /**
     * 중복 확인
     */
    @Test
    public void 회원가입() throws Exception {
        //given
        MemberSaveRequestDto member = new MemberSaveRequestDto();
        Address address = new Address("경기도", "광명시", "어딘가");

        //when
       Long id = memberService.SignUp(member.builder()
                .name("현우")
                .address(address)
                .birth("1995-11-29")
                .email("gachon@mail.com")
                .password("12345")
                .phone("010-111-222")
                .role(Role.GUEST)
                .build());

        Member T = em.find(Member.class, id);

        //then
        assertEquals(T.getId(), id);
    }
//

//    @Test(expected = IllegalStateException.class) 해당구문이현재없다
    @Test
    public void 중복회원가입() throws Exception {
        //given
        MemberSaveRequestDto member = new MemberSaveRequestDto();
        Address address = new Address("경기도", "광명시", "어딘가");
        //given + when
        //when
        Long id = memberService.SignUp(member.builder()
                .name("현우")
                .address(address)
                .birth("1995-11-29")
                .email("gachon@mail.com")
                .password("12345")
                .phone("010-111-222")
                .role(Role.GUEST)
                .build());

        em.flush();
        //then
        fail("예외가 발생해야 한다");
        assertEquals(member.getEmail(), member.getEmail());
    }
//
//
//    @Test
//    @Rollback(false)
//    public void 회원수정() throws Exception {
//
//        Member member = memberRepository.findOne(10L);
//
//        Address address = new Address("abc", "kfc", "def");
//        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
//                .password("12345")
//                .build();
//        memberService.update(10L, requestDto);
//
//        String url = "http://localhost:" + port + "/api/member/settings/10";
//
//
//    }


}

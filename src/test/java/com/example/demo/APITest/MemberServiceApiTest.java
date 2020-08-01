package com.example.demo.APITest;

import com.example.demo.APITest.MockTest.MemberApiMockTest;
import com.example.demo.config.security.Role;
import com.example.demo.disease.domain.Disease;
import com.example.demo.member.controller.MemberController;
import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import com.example.demo.member.dto.MemberSaveRequestDto;
import com.example.demo.member.dto.MemberUpdateRequestDto;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.service.MemberService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //내장톰켓 구동
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //내장톰켓 구동 안함 디폴트값
//@WebMvcTest
@Transactional
public class MemberServiceApiTest {

    /**
     * MOCK :
     * 서블릿컨테이너를 Mocking 한다 DispatchServlet 에다 요청을 보내는것과 비스무리하게
     * 실험을 한다. 목업이 된 서블릿에 어떠한 인터렉션을 하려면 목mvc라는 클라이언트를 꼭 사용해야한다.
     *
     */


    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
//
//
//    @Autowired
//    private WebApplicationContext context;
//


    @PersistenceContext
    EntityManager em;


    //테스트코드 실행 전에 수행
    @Before
    public void setUp(){
        System.out.println("BEFORE-------");

    }
    //테스트코드 실행 후에 수행
    @After
    public void setAfter(){
        System.out.println("After--------");
    }

    /**
     * 회원가입
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
        assertEquals(T.getId(), id); //두 값이 같은지 확인하기
    }





    @Test(expected = IllegalStateException.class)// catch 문구를 볼필요가 없을경우.
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
                .email("admin@mail.com")
                .password("12345")
                .phone("010-111-222")
                .role(Role.GUEST)
                .build());

//        em.flush();
        //then
        fail("예외가 발생해야 한다"); //호출즉시 테스트케이스가 실패로 판정하는 단정문

    }

    @Test
    @Rollback(false)
    public void 회원수정() throws Exception {
        //given
        Address address = new Address("kk", "kfc", "def");
        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
                .city(address.getCity())
                .zipcode(address.getZipcode())
                .street(address.getStreet())
                .build();
        memberService.update(2L, requestDto);
        Member m = em.find(Member.class, 2L);



        String url = "http://localhost:" + port + "/api/member/settings/"+m.getId();

        HttpEntity<MemberUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        String result = restTemplate.getForObject("/",String.class); //get 메소드 리턴값 확인

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,
                                                requestEntity, Long.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("ResponseEntity 상태코드 : " + responseEntity.getStatusCode());
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.get(0).getAddress().getCity()).isEqualTo("kk"); //Hamcrest

    }


    @Test(expected = AssertionError.class)
    public void 회원삭제() throws Exception{
        Member member = em.find(Member.class ,97L);
        memberService.delete(member.getId());
        String url = "http://localhost:" + port + "/api/member/delete/"+member.getId();

        restTemplate.delete(url);
        em.find(Member.class, 97L);

        fail("사용자 없음"); //expected 제외할 경우 해당 문구가 뜬다.
    }
}

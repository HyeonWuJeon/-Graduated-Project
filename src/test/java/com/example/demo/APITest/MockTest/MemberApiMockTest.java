package com.example.demo.APITest.MockTest;

import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import com.example.demo.member.dto.MemberUpdateRequestDto;
import com.example.demo.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.LazyToOne;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 참고!
 */
//https://cheolhojung.github.io/posts/java/springboot-test.html

@RunWith(SpringRunner.class)
//@restController
//@WebMvcTest(MemberApiController.class) //슬라이싱 테스트. 각레이어별로 빈이 등록된다. Service,Repository 등록이 안된다
//@WebMvcTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient //webTestClient 빈을 만들어준다!
@AutoConfigureMockMvc //McokMvc라는 클라이언트를 사용한다 -->
@Transactional
public class MemberApiMockTest {


    /**
     * 서블릿기반에 웹MVC가아닌 비동기Call을 하는 web-flux
     * 아래의 WebTestClient는 논블럭킹 + 비동기 call을 지원한다. restTemplate의 대체자로 불린다.
     */
    @Autowired
    WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @MockBean
    MemberService memberService;

    /**
     * Mocking : MemberService
     * 해당 빈을 MockBean을 교체한다. memberService를 사용하는 컨트롤러는 mocking된 아래의 memberService를 사용한다.
     * 즉 아래의memberService는 원본이 아니다
     * 프로젝트 전체를 빌드하면서 컴포넌트스캔을 해주고 아래의 mockbean으로 설정한 memberService만 교체해준다.
     */

//    @Autowired
//    MemberService memberService;

    /**
     * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
     * 서블릿컨테이너를 Mocking 한다 DispatchServlet 에다 요청을 보내는것과 비스무리하게
     * 실험을 한다. 목업이 된 서블릿에 어떠한 인터렉션을 하려면 목mvc라는 클라이언트를 꼭 사용해야한다.
     *
     */

    /**
     * 실제로 서블릿이뜬다. MOCKMVC가 아닌 테스트용 TestRestemplate, webclient를 적용해야한다.
     * 겹치지않는 랜덤한 포트에 뜬다.
     */
    @Autowired
    private MockMvc mockMvc; //주입받기


    @PersistenceContext
    EntityManager em;

    /**
     * FUNCTION : ResponseEntity Entity반환 TEST
     * @throws Exception
     */
    @Test
    public void ApiTEst() throws Exception {

        mockMvc.perform(get("/api/Test.htm"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value("현우"))
                .andDo(print())
                .andReturn();

    }

//    @Test
//    public void MockingWebFlux() throws Exception {
//        when(memberService.mockTest()).thenReturn("hyunwoo");
//        webTestClient.get().uri("/mockTest").exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class).isEqualTo("hyunwoo");
//    }

    @Test
    public void 회원수정WebFlux() throws Exception {

        //when
        Address address = new Address("kk", "kfc", "def");
        MemberUpdateRequestDto requestDto = MemberUpdateRequestDto.builder()
                .city(address.getCity())
                .zipcode(address.getZipcode())
                .street(address.getStreet())
                .build();

        //        when(memberService.update(2L, requestDto)).thenReturn(2L); //mocking --> @mockBean

        String url = "http://localhost:" + port + "/api/member/delete/2";

        //given
        Member member = em.find(Member.class, 2L);
        webTestClient.put().uri(url)
//                .body(requestDto, MemberUpdateRequestDto.class)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }


    @Test(expected = AssertionError.class)
    public void 회원삭제WebFlux() throws Exception {

        Member member = em.find(Member.class, 97L);
        memberService.delete(member.getId());
        String url = "http://localhost:" + port + "/api/member/delete/" + member.getId();

        webTestClient.delete().uri(url)
                .exchange()
                .expectStatus().isOk();
//        restTemplate.delete(url);
        em.find(Member.class, 97L);

        fail("사용자 없음"); //expected 제외할 경우 해당 문구가 뜬다.
    }

    @Test
    public void checkEmail() throws Exception {
        //given
        String email = "admin5@gmail.com";
        Member member = new Member();
        member.setEmail(email);
        em.persist(member);
        em.flush();
        String url = "http://localhost:" + port + "/api/checkEmail";
        //when
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(email)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

package com.example.demo.APITest;


import com.example.demo.dog.domain.Dog;
import com.example.demo.dog.dto.DogSaveRequestDto;
import com.example.demo.dog.repository.DogRepository;
import com.example.demo.dog.service.DogService;
import com.example.demo.member.domain.Member;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //내장톰켓 구동
//@AutoConfigureMockMvc
@Transactional
public class DogServiceApiTest {

//    https://jojoldu.tistory.com/226



    @LocalServerPort
    int port;
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private DogService dogService;

    @Autowired
    private TestRestTemplate restTemplate;

    private Member member;

    //given
    static private String name= "왈왈";
    static private String age = "13살";
    static private String gender="수컷";
    static private String birth="19951129";
    static private String type="포메라니안";


    @Before
    public void Start(){
        System.out.println("-------------테스트 시작-----------");
        dogRepository.deleteAll();

    }
    @After
    public void Delete() {
        dogRepository.deleteAll();
        System.out.println("-------------테스트 종료-----------");
    }

    @Test
    public void JPA테스트() {

        //given
        DogSaveRequestDto dog = new DogSaveRequestDto();
        Long TestId = dogService.dog_SignUp(dog.builder()
                .name(name)
                .birth(birth)
                .type(type)
                .gender(gender)
                .age(age)
                .build());

//        em.persist(dog);
        dogRepository.findById(TestId); // when
        assertThat(dog.getId()).isEqualTo(TestId); // then
    }

    @Test
    public void 저장API () {

        //given
        DogSaveRequestDto dog = new DogSaveRequestDto();
        Long TestId = dogService.dog_SignUp(dog.builder()
                .name(name)
                .birth(birth)
                .type(type)
                .gender(gender)
                .age(age)
                .build());

        System.out.println("dog.getId() = " + dog.getId());


        String url = "http://localhost:" + port + "/api/member/dog/save";

        HttpEntity<DogSaveRequestDto> requestEntity = new HttpEntity<>(dog);


        //when
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
                requestEntity, String.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("ResponseEntity 상태코드 : " + responseEntity.getStatusCode());
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        List<Dog> dogList= dogRepository.findAll();

        assertThat(dogList.get(0).getAge()).isEqualTo(age);

    }




}

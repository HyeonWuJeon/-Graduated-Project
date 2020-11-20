package com.example.demo.APITest.wefluxTest;

import com.example.demo.config.aop.LogExecutionTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, properties = {"server.port=8080"})
@AutoConfigureWebTestClient
public class WefluxApiTest {

    private static final String URL = "http://localhost:80/test";
    private static final int LOOP_COUNT = 100;

    private final WebClient webClient = WebClient.create();
    private final CountDownLatch count = new CountDownLatch(LOOP_COUNT);

    @Autowired
    WebTestClient webTestClient;

    @Before
    public void setup() {
        System.setProperty("reactor.netty.ioWorkerCount", "1");
    }

    /**
     * AI 서비스 테스트
     */
    @Test
    public void blocking() {
        //given
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("증상0", "거리감각불능");
        parameters.add("증상1", "탈모");
        parameters.add("증상2", "난청");

        final RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = new HttpEntity(parameters,parameters);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final ResponseEntity<String> response =
                restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);
//        assertThat(response.getBody()).contains("success");
        stopWatch.stop();
//
        System.out.println(" 총 시간 : " + stopWatch.getTotalTimeSeconds());
    }
}
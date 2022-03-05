package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.repository.HoleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HoleControllerTest {

    private static final String HOLE = "/hole/1";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    HoleRepo holeRepo;

    @BeforeEach
    public void cleanup() {
        holeRepo.deleteAll();
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    public <T> ResponseEntity<T> postSignup(Object request, Class<T> response){
        return testRestTemplate.postForEntity(HOLE, request, response);
    }


}

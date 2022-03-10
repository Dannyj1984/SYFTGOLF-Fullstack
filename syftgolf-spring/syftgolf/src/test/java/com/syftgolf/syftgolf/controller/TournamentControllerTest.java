package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.TestUtil;
import com.syftgolf.syftgolf.entity.Society;
import com.syftgolf.syftgolf.entity.Tournament;
import com.syftgolf.syftgolf.repository.SocietyRepo;
import com.syftgolf.syftgolf.repository.TournamentRepo;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TournamentControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    TournamentRepo tournamentRepo;

    @Autowired
    SocietyRepo societyRepo;

    @Before
    public void cleanup() {
        tournamentRepo.deleteAll();
        societyRepo.deleteAll();
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    @Test
    public void postTournament_whenTournamentIsValid_receiveOk() {
        Society s = TestUtil.createValidSociety();
        s.setName("test-society1");
        societyRepo.save(s);
        Tournament tournament = TestUtil.createValidTournament();
        ResponseEntity<Object> response = testRestTemplate.postForEntity("/api/1.0/management/tournament/create/1", tournament, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postTournament_whenTournamentIsValid_saveToDatabase() {
        societyRepo.save(TestUtil.createValidSociety());
        Tournament tournament = TestUtil.createValidTournament();
        testRestTemplate.postForEntity("/api/1.0/management/tournament/create/1", tournament, Object.class);
        assertThat(tournamentRepo.count()).isEqualTo(1);
    }



}

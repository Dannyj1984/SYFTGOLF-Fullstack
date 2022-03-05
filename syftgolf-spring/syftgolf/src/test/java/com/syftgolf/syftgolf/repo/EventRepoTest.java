package com.syftgolf.syftgolf.repo;

import com.syftgolf.syftgolf.TestUtil;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Society;
import com.syftgolf.syftgolf.repository.EventRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class EventRepoTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    EventRepo eventRepo;

    @Test
    public void getEventsDetails_whenEventExists_ReturnEventDetailsByName() {
        Society s = TestUtil.createValidSociety();
        testEntityManager.persist(s);
        Event e = TestUtil.createValidStablefordEvent();
        e.setSociety(s);
        testEntityManager.persist(e);

        Event event = eventRepo.findEventByName("Test-event");
        assertThat(event).isNotNull();

    }
}

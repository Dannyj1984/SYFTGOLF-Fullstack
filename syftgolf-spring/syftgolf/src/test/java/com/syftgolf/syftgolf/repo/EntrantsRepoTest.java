package com.syftgolf.syftgolf.repo;

import com.syftgolf.syftgolf.TestUtil;
import com.syftgolf.syftgolf.entity.Course;
import com.syftgolf.syftgolf.entity.Entrants;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.repository.CourseRepo;
import com.syftgolf.syftgolf.repository.EntrantsRepo;
import com.syftgolf.syftgolf.repository.EventRepo;
import com.syftgolf.syftgolf.repository.MemberRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class EntrantsRepoTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    EntrantsRepo entrantsRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    MemberRepo memberRepo;


    @Test
    public void getEntrantList_ifAnEventHasEntrants_returnEntrantsList() {
        testEntityManager.persist(new Course("Glen", 72, 69.2, 121));
        testEntityManager.persist(new Event("Glen", LocalDate.of(2022, Month.OCTOBER, 1) , 156, "Stableford", "Some info", 20.00, true,true, courseRepo.findCourseByName("Glen")));
        testEntityManager.persist(TestUtil.createValidMember());
        Entrants en = new Entrants(memberRepo.findMemberByUsername("test-user"), eventRepo.findEventByName("Glen"), 0, 0);
        List<Entrants> len = new ArrayList<>();
        len.add(en);
        Event ev = eventRepo.findEventByName("Glen");
        ev.setEntrants(len);
        testEntityManager.persist(ev);
        List<Entrants> entrantList = entrantsRepo.findAllByEvent(ev);
        assertThat(entrantList).isNotNull();
    }

    @Test
    public void getMemberDetails_ifEventHasEntrants_returnMemberDetailsForASpecificMember() {
        testEntityManager.persist(new Course("Glen", 72, 69.2, 121));
        testEntityManager.persist(new Event("Glen", LocalDate.of(2022, Month.OCTOBER, 1) , 156, "Stableford", "Some info", 20.00, true,true, courseRepo.findCourseByName("Glen")));
        testEntityManager.persist(TestUtil.createValidMember());
        Entrants en = new Entrants(memberRepo.findMemberByUsername("test-user"), eventRepo.findEventByName("Glen"), 0, 0);
        List<Entrants> len = new ArrayList<>();
        len.add(en);
        Event ev = eventRepo.findEventByName("Glen");
        ev.setEntrants(len);
        testEntityManager.persist(ev);
        testEntityManager.persist(en);
        Entrants entrant = entrantsRepo.getEntrantsByMemberAndEvent(memberRepo.findMemberByUsername("test-user"), eventRepo.findEventByName("Glen"));
        Member member = entrant.getMember();
        assertThat(member.getUsername()).isEqualTo("test-user");
    }

    @Test
    public void deleteEntrant_whenMemberExistsInEntrants_RemoveEntrant() {
        testEntityManager.persist(new Course("Glen", 72, 69.2, 121));
        testEntityManager.persist(new Event("Glen", LocalDate.of(2022, Month.OCTOBER, 1) , 156, "Stableford", "Some info", 20.00, true, true, courseRepo.findCourseByName("Glen")));
        testEntityManager.persist(TestUtil.createValidMember());
        Entrants en = new Entrants(memberRepo.findMemberByUsername("test-user"), eventRepo.findEventByName("Glen"), 0, 0);
        List<Entrants> len = new ArrayList<>();
        len.add(en);
        Event ev = eventRepo.findEventByName("Glen");
        ev.setEntrants(len);
        testEntityManager.persist(ev);
        testEntityManager.persist(en);
        entrantsRepo.deleteByMemberAndEvent(memberRepo.findMemberByUsername("test-user"), ev);
        List<Entrants> updatedEntrants = entrantsRepo.findAllByEvent(ev);
        assertThat(updatedEntrants).isEmpty();
    }


}

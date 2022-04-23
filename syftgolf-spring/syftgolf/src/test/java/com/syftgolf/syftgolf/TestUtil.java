package com.syftgolf.syftgolf;

import com.syftgolf.syftgolf.entity.*;
import com.syftgolf.syftgolf.repository.SocietyRepo;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class TestUtil {

    public static Member createValidMember() {
        Member member = new Member();
        member.setPassword("P4ssword");
        member.setUsername("test-user");
        member.setEmail("test@email.com");
        member.setFirstName("test");
        member.setSurname("user");
        member.setHandicap(10.0);
        member.setMobile("07956356879");
        member.setCdh("1013530000");
        member.setSocHcpRed(1);
        member.setHomeClub("Augusta");
        member.setSocHcp(5.2);
        member.setWins(0);
        member.setRole("USER");
        return member;
    }

    public static Member createValidUser(String username) {
        Member member = createValidMember();
        member.setUsername(username);
        return member;
    }

    public static Society createValidSociety() {
        return new Society("Test-Society");
    }

    public static Course createValidCourse() {
        return new Course("Glen", 72, 69.2, "SK15 3DT", "GB", 121);
    }

    public static Event createValidStablefordEvent() {
        Event e = new Event("Test-event", LocalDate.of(2022, Month.OCTOBER, 1) , 156, "Stableford");
        return e;
    }

    public static Tournament createValidTournament() {
        Tournament t = new Tournament();
        t.setName("Test tournament");
        t.setStartDate(LocalDate.of(2022, Month.SEPTEMBER, 30));
        t.setEndDate(LocalDate.of(2022, Month.OCTOBER, 2));
        t.setType("Stableford");
        return t;
    }

}

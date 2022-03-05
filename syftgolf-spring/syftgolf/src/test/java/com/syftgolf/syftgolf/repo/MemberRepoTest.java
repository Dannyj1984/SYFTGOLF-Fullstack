package com.syftgolf.syftgolf.repo;

import com.syftgolf.syftgolf.TestUtil;
import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.Society;
import com.syftgolf.syftgolf.repository.MemberRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class MemberRepoTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    MemberRepo memberRepo;

    @Test
    public void findByUsername_whenUserExists_returnUsers() {
        Society s = TestUtil.createValidSociety();
        Member m = TestUtil.createValidMember();
        testEntityManager.persist(s);
        m.setSociety(s);
        testEntityManager.persist(m);
        Member inDB = memberRepo.findMemberBySocietyIdAndUsername(s.getId(), "test-user");
        assertThat(inDB).isNotNull();
    }

    @Test
    public void findByEmail_whenEmailExists_returnUsers() {
        Society s = TestUtil.createValidSociety();
        Member m = TestUtil.createValidMember();
        testEntityManager.persist(s);
        m.setSociety(s);
        testEntityManager.persist(m);

        Member inDB = memberRepo.findMemberByEmail("test@email.com");
        assertThat(inDB).isNotNull();
    }

    @Test
    public void findByUsername_whenUsernameDoesNotExist_returnsNull() {
        Member inDB = memberRepo.findMemberByUsername("nonExistingUser");
        assertThat(inDB).isNull();
    }

    @Test
    public void findByEmail_whenEmailDoesNotExist_returnsNull() {
        Member inDB = memberRepo.findMemberByEmail("non@gmail.com");
        assertThat(inDB).isNull();
    }

    @Test
    @DisplayName("Test findById Not Found")
    public void findById_whenIdDoesNotExist_returnsNull() {
        Optional<Member> member = memberRepo.findById(2L);
        Assertions.assertFalse(member.isPresent(), "Member should not be found");

    }

}

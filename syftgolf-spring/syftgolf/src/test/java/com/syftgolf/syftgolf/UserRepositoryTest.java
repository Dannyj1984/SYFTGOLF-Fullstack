package com.syftgolf.syftgolf;

import static org.assertj.core.api.Assertions.assertThat;

import com.syftgolf.syftgolf.user.User;
import com.syftgolf.syftgolf.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUsername_whenUserExists_returnUsers() {

        testEntityManager.persist(TestUtil.createValidUser());

        User inDB = userRepository.findByUsername("test-user");
        assertThat(inDB).isNotNull();
    }

    @Test
    public void findByEmail_whenEmailExists_returnUsers() {
        testEntityManager.persist(TestUtil.createValidUser());

        User inDB = userRepository.findUserByEmail("test@email.com");
        assertThat(inDB).isNotNull();
    }

    @Test
    public void findByUsername_whenUsernameDoesNotExist_returnsNull() {
        User inDB = userRepository.findByUsername("nonExistingUser");
        assertThat(inDB).isNull();
    }

    @Test
    public void findByEmail_whenEmailDoesNotExist_returnsNull() {
        User inDB = userRepository.findUserByEmail("non@gmail.com");
        assertThat(inDB).isNull();
    }
}


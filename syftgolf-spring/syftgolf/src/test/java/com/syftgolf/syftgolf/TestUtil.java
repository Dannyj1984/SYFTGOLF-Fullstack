package com.syftgolf.syftgolf;

import com.syftgolf.syftgolf.user.User;

public class TestUtil {

    public static User createValidUser() {
        User user = new User();
        user.setPassword("P4ssword");
        user.setUsername("test-user");
        user.setEmail("test@email.com");
        user.setFirstname("test");
        user.setSurname("user");
        user.setHandicap(10.0);
        user.setMobile("07956356879");
        user.setCdh("1013530000");
        user.setSochcpred(1);
        user.setHomeclub("Augusta");
        user.setSochcp(5.2);
        user.setWins(0);
        user.setRole("USER");
        return user;
    }

    public static User createValidUser(String username) {
        User user = createValidUser();
        user.setUsername(username);
        return user;
    }
}

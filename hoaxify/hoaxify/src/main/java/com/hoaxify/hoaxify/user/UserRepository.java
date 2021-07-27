package com.hoaxify.hoaxify.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //Find any existing users in the DB with the username, thsi will produce sql query to find all users with this username
    User findByUsername(String username);

}

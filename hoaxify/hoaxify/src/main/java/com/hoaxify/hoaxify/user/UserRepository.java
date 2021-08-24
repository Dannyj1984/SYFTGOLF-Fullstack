package com.hoaxify.hoaxify.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    //return User with this username
    User findByUsername(String username);

    //Return User with this email
    User findUserByEmail(String email);

}

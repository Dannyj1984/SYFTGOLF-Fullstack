package com.hoaxify.hoaxify.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    //return User with this username
    User findByUsername(String username);

    //Return User with this email
    User findUserByEmail(String email);

    Page<User> findByUsernameNot(String username, Pageable page);

    @Query(value = "select u from User u")
    List<User> findAllUsers(Sort sort);

    User findById(int id);

}

package com.syftgolf.syftgolf.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    //Return list of society users and sort
    List<User> findAllBySocietyId(long id, Sort sort);

    //return User with this username
    User findByUsername(String username);

    //Return page of users filtered by username
    Page<User> findByUsernameStartsWithAndSocietyId(String query, Pageable page, long id);

    //Get Society members
    public Page<User> findAllBySocietyId(Pageable page, long id);

    //Return User with this email
    User findUserByEmail(String email);

    //Return all users except for the current user
    Page<User> findByUsernameNot(String username, Pageable page);

    //Find all users
    @Query(value = "select u from User u")
    List<User> findAllUsers(Sort sort);

    //Find user by an id
    User findById(int id);


    //Find all users for a society
    @Query(value = "SELECT * FROM member  WHERE society_id=:id ", nativeQuery = true)
    Page<User> findAllSocietyUsers(Pageable page, long id);



}

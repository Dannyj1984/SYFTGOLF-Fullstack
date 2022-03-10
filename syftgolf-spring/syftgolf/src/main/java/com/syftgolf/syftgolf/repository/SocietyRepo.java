package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SocietyRepo extends JpaRepository<Society, Long> {

    Society findAllByName(String name);

    //reset id value for testing only
    @Query(value = "TRUNCATE society RESTART IDENTITY CASCADE", nativeQuery = true)
    void resetId();
}

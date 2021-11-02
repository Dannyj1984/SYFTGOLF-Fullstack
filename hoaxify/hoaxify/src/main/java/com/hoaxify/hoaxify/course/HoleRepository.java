package com.hoaxify.hoaxify.course;

import com.hoaxify.hoaxify.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoleRepository extends JpaRepository<Hole, Long> {

    //Get all holes
    @Query(value = "SELECT h FROM Hole h WHERE h.course=3")
    List<Hole> findAllHoles(Sort sort);
}

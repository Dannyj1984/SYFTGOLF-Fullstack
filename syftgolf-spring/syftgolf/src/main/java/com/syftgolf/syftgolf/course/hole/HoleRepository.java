package com.syftgolf.syftgolf.course.hole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoleRepository extends JpaRepository<Hole, Long> {

    //Get all holes
    @Query(value = "SELECT * FROM hole WHERE hole.course_id=29 ORDER BY hole asc", nativeQuery = true)
    List<Hole> findAllHoles(@Param("course_id") long course_id);
}

package com.syftgolf.syftgolf.course.hole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoleRepository extends JpaRepository<Hole, Long> {

    //Get all holes
    @Query("SELECT h FROM Hole h WHERE h.course.courseid=:course_id")
    List<Hole> findAllHoles(@Param("course_id") long course_id);
}

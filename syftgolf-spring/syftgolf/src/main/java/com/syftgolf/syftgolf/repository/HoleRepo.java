package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Course;
import com.syftgolf.syftgolf.entity.Hole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoleRepo extends JpaRepository<Hole, Long> {

    List<Hole> findAllByCourse(Course course);

    List<Hole> findAllByCourseOrderByHoleNumberAsc(Course course);


}

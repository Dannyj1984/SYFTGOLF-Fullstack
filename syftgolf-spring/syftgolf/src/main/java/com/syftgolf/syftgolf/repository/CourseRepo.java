package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CourseRepo extends JpaRepository<Course, Long> {

    List<Course> findAllBySocietyId(long id);

    Course findCourseByName(String name);

    Course findById(long id);

    Page<Course> findByNameContainsAndSocietyId(String query, Pageable pageable, long id);

    List<Course> findAllBySocietyIdOrderByNameAsc(long id);

    Course findCourseById(long id);
}

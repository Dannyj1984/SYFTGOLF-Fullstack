package com.syftgolf.syftgolf.course;

import com.syftgolf.syftgolf.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    //return User with this username
    Course findByCourseName(String courseName);

    //Return page of courses with filter
    Page<Course> findByCourseNameStartsWithIgnoreCaseAndSocietyId(String query, Pageable page, long id);

    //Reutn a list of courses for a society
    public List<Course> findAllBySocietyIdOrderByCourseNameAsc(long id);

    //
    Page<Course> findByCourseNameNot(String courseName, Pageable page);

    //Return a page of all courses for a society
    public Page<Course> findAllBySocietyId(Pageable page, long id);

}

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


     List<Course> findAllByOrderByCourseNameAsc();

     public List<Course> findAllBySocietyIdOrderByCourseNameAsc(long id);

    Page<Course> findByCourseNameNot(String courseName, Pageable page);

    public Page<Course> findAllBySocietyId(Pageable page, long id);

}

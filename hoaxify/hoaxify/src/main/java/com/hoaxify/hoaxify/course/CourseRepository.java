package com.hoaxify.hoaxify.course;

import com.hoaxify.hoaxify.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    //return User with this username
    Course findByCourseName(String courseName);



    Page<Course> findByCourseNameNot(String courseName, Pageable page);

}

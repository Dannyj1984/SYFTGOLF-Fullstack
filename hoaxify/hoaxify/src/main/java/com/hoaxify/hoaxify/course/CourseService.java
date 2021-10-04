package com.hoaxify.hoaxify.course;

import com.hoaxify.hoaxify.course.vm.CourseUpdateVM;
import com.hoaxify.hoaxify.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseService {

    CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Page<Course> getCourses(Pageable pageable ) {

        return courseRepository.findAll(pageable);
    }

    public Course getByCourseName(String courseName) {
        Course inDB = courseRepository.findByCourseName(courseName);
        if(inDB == null) {
            throw new NotFoundException(courseName + " not found");
        }
        return inDB;
    }

    public Course updateCourse(long id, CourseUpdateVM courseUpdate) {
        Course inDB = courseRepository.getOne(id);
        inDB.setCourseName(courseUpdate.getCourseName());
        String savedImageName = inDB.getCourseName() + UUID.randomUUID().toString().replaceAll("-", "");
        inDB.setImage(savedImageName);
        return courseRepository.save(inDB);
    }

    public void deleteCourse(long id) {
        Course course = courseRepository.getOne(id);
        courseRepository.deleteById(id);

    }


}

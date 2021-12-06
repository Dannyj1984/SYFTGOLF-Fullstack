package com.syftgolf.syftgolf.course;

import com.syftgolf.syftgolf.course.vm.CourseUpdateVM;
import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.file.FileService;
import com.syftgolf.syftgolf.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class CourseService {

    CourseRepository courseRepository;

    FileService fileService;

    public CourseService(CourseRepository courseRepository, FileService fileService) {
        this.courseRepository = courseRepository;
        this.fileService = fileService;
    }

    //Save a new course
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    //Get a page of courses
    public Page<Course> getCourses(Pageable pageable ) {
        return courseRepository.findAll(pageable);
    }

    //Get a course by coursename to show on course profile card
    public Course getByCourseName(String courseName) {
        Course inDB = courseRepository.findByCourseName(courseName);
        if(inDB == null) {
            throw new NotFoundException(courseName + " not found");
        }
        return inDB;
    }

    //Update course details
    public Course updateCourse(long id, CourseUpdateVM courseUpdate) {
        Course inDB = courseRepository.getOne(id);
        inDB.setCourseName(courseUpdate.getCourseName());
        inDB.setCourseRating(courseUpdate.getCourseRating());
        inDB.setSlopeRating(courseUpdate.getSlopeRating());
        inDB.setPar(courseUpdate.getPar());
        if(courseUpdate.getImage() != null) {
            String savedImageName;
            try {
                savedImageName = fileService.saveProfileImage(courseUpdate.getImage());
                inDB.setImage(savedImageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return courseRepository.save(inDB);
    }

    //Delete a course
    public void deleteCourse(long id) {
        Course course = courseRepository.getOne(id);
        courseRepository.deleteById(id);

    }

    //Get a page of courses for a society
    public Page<Course> getCoursesForSociety(Pageable pageable, long id) {
        return courseRepository.findAllBySocietyId(pageable, id);
    }

    //Page of filtered courses for a society
    public Page<Course> getFilteredCourses(String query, Pageable pageable, long id) {
        return courseRepository.findByCourseNameStartsWithIgnoreCaseAndSocietyId(query, pageable, id);
    }


}

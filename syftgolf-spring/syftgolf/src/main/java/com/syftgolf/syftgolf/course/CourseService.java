package com.syftgolf.syftgolf.course;

import com.syftgolf.syftgolf.course.vm.CourseUpdateVM;
import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.file.FileService;
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

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Page<Course> getCourses(Pageable pageable ) {
        System.out.println(courseRepository.findAll(pageable));
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

    public void deleteCourse(long id) {
        Course course = courseRepository.getOne(id);
        courseRepository.deleteById(id);

    }


}

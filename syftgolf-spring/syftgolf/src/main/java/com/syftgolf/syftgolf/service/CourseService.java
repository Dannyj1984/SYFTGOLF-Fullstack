package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.Course;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Society;
import com.syftgolf.syftgolf.entity.vm.course.CourseVM;
import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.repository.CourseRepo;
import com.syftgolf.syftgolf.repository.SocietyRepo;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    /**
     * Save a course
     * Get a page of courses
     * Update a course
     * Delete a course
     */

    CourseRepo courseRepo;

    SocietyRepo societyRepo;

    public CourseService(CourseRepo courseRepo, SocietyRepo societyRepo) {
        this.courseRepo = courseRepo;
        this.societyRepo = societyRepo;
    }

    /**
     *
     * @param course object to save
     * @param id id of the society
     * @return the saved course
     */
    public Course save(Course course, long id){
        Society sInDb = societyRepo.getOne(id);
        course.setSociety(sInDb);
        return courseRepo.save(course);
    }

    //Get a page of events
    public Page<Course> getCourses(Pageable pageable, long id) {
        return courseRepo.findCourseBySocietyId(pageable, id);
    }

    /**
     *
     * @param courseId id of the course to update
     * @param course body of the course to update
     * @return a message to inform of successful update
     */
    public GenericResponse update(long courseId, CourseVM course) {
        Course c = courseRepo.getById(courseId);
        c.setCourseRating(course.getCourseRating());
        c.setName(course.getName());
        c.setPar(course.getPar());
        c.setSlopeRating(course.getSlopeRating());
        courseRepo.save(c);

        return new GenericResponse("Course updated");
    }
    //Page of filtered courses for a society
    public Page<Course> getFilteredCourses(String query, Pageable pageable, long id) {
        return courseRepo.findByNameContainsAndSocietyId(query, pageable, id);
    }

    public Course getByCourseName(String courseName) {
        Course inDB = courseRepo.findCourseByName(courseName);
        if(inDB == null) {
            throw new NotFoundException(courseName + " not found");
        }
        return inDB;
    }

    public List<Course> getCourseList(long societyId) {
        return courseRepo.findAllBySocietyId(societyId);
    }
}

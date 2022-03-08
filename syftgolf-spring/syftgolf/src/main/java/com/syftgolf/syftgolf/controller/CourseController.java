package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.Course;
import com.syftgolf.syftgolf.entity.vm.course.CourseVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.CourseRepo;
import com.syftgolf.syftgolf.service.CourseService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/1.0")
public class CourseController {

    /**
     * Get a list of courses
     * Get a single course by its name
     * Save a new course
     * Update a course
     * Delete a course
     */

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    CourseService courseService;

    /**
     *
     * @return a list of courses for a society
     */
    
//    @GetMapping("courses/{societyId:[0-9]+}")
//    List<Course> getCourses(@PathVariable long societyId) {
//        return courseRepo.findAllBySocietyId(societyId);
//    }

    @GetMapping("/getCourses/{id:[0-9]+}")
    Page<CourseVM> getCourses(Pageable page, @PathVariable long id) {
        List<Course> courses;
        return courseService.getCourses(page, id).map(CourseVM::new);
    }
    @GetMapping("/societyFilteredCourses/{id:[0-9]+}")
    Page<Course> getFilteredCourses(Pageable page, @PathVariable long id, @RequestParam String query) {
        return courseService.getFilteredCourses(query, page, id);
    }

    /**
     *
     *
     * @param courseName name of the course to find
     * @return the course details
     */

    @GetMapping("/course/{courseName}")
    Course getCourse(@PathVariable String courseName) {
        System.out.println(courseName);
        return courseService.getByCourseName(courseName);
    }


    /**
     *
     * @param course the Course object sent to the backend
     * @param societyId The ID of the society to link the course to
     * @return the saved course
     */

    @PostMapping("/management/course/{societyId:[0-9]+}")
    Course save(@RequestBody @Valid Course course, @PathVariable long societyId) {
        return courseService.save(course, societyId);
    }

    /**
     *
     * @param courseId id of the course to update
     * @param course body of the course to update
     * @return a message to inform of successful update
     */

    @PutMapping("/management/course/update/{courseId/[0-9]+}")
    GenericResponse update(@PathVariable long courseId, @Valid @RequestBody Course course) {
        return courseService.update(courseId, course);
    }

    /**
     *
     * @param courseId id of course to delete
     * @return a message to inform of successful deletion
     */

    @DeleteMapping("/management/course/delete/{courseId:[0-9]+}")
    GenericResponse delete(@PathVariable long courseId) {
        courseRepo.delete(courseRepo.getById(courseId));
        return new GenericResponse("Course deleted");
    }

    
    @ExceptionHandler({MethodArgumentNotValidException.class})
    //Tell spring to return 400 (BAD REQUEST)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(400, "Validation error", request.getServletPath());

        BindingResult result = exception.getBindingResult();

        Map<String, String> validationErrors = new HashMap<>();

        for(FieldError fieldError: result.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);

        return apiError;
    }
}

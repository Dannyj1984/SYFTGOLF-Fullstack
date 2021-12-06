package com.syftgolf.syftgolf.course;

import com.syftgolf.syftgolf.course.vm.CourseUpdateVM;
import com.syftgolf.syftgolf.course.vm.CourseVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.shared.GenericResponse;
import com.syftgolf.syftgolf.user.User;
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


@RestController
@CrossOrigin
@RequestMapping("/api/1.0")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;


    //Create new course for society
    @PostMapping("/management/courses")
    @CrossOrigin
    GenericResponse createCourse(@Valid @RequestBody Course course) {
        courseService.save(course);
        return new GenericResponse("Course saved");
    }

    //For listing courses in the add new event page
    @GetMapping("/management/getCourses/{id:[0-9]+}")
    @CrossOrigin
    List<Course> getCourses(@PathVariable long id) {
        List<Course> courses;
        courses = courseRepository.findAllBySocietyIdOrderByCourseNameAsc(id);
        return courses;
    }

    //Get Page of courses for this society
    @GetMapping("/getCourses/{id:[0-9]+}")
    @CrossOrigin
    Page<CourseVM> getCourses(Pageable page, @PathVariable long id) {
        return courseService.getCoursesForSociety(page, id).map(CourseVM::new);
    }

    //Get page of courses for a society filtered by coursename containing
    @CrossOrigin
    @GetMapping("/societyFilteredCourses/{id:[0-9]+}")
    Page<Course> getFilteredCourses(Pageable page, @PathVariable long id, @RequestParam String query) {
        return courseService.getFilteredCourses(query, page, id);
    }

    //Get course by name for course profile card

    @GetMapping("/courses/{courseName}")
    @CrossOrigin
    CourseVM getCourseByName(@PathVariable String courseName) {
        Course course = courseService.getByCourseName(courseName);
        return new CourseVM(course);
    }

    //Update a course
    @PutMapping("/management/courses/{id:[0-9]+}")
    @CrossOrigin
    CourseVM updateCourse(@PathVariable long id, @Valid @RequestBody(required = false) CourseUpdateVM courseUpdate) {
        Course updated = courseService.updateCourse(id, courseUpdate);
        return new CourseVM(updated);
    }

    //Delete a course
    @DeleteMapping("/management/courses/delete/{id:[0-9]+}")
    @CrossOrigin
    GenericResponse deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
        return new GenericResponse("Course has been removed");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
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

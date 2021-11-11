package com.syftgolf.syftgolf.course;

import com.syftgolf.syftgolf.course.vm.CourseUpdateVM;
import com.syftgolf.syftgolf.course.vm.CourseVM;
import com.syftgolf.syftgolf.error.ApiError;
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


@RestController
@RequestMapping("/api/1.0")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @PostMapping("/management/courses")
    GenericResponse createCourse(@Valid @RequestBody Course course) {
        courseService.save(course);
        return new GenericResponse("Course saved");
    }

    //For listing courses in the add new event page
    @GetMapping("/management/courses")
    List<Course> getCourses() {

        List<Course> courses;
        courses = courseRepository.findAllByOrderByCourseNameAsc();
        return courses;
    }

    @GetMapping("/courses")
    Page<CourseVM> getCourses(Pageable page) {
        return courseService.getCourses(page).map(CourseVM::new);
    }

    @GetMapping("/courses/{courseName}")
    CourseVM getCourseByName(@PathVariable String courseName) {
        Course course = courseService.getByCourseName(courseName);
        return new CourseVM(course);
    }

    @PutMapping("/management/courses/{id:[0-9]+}")
    CourseVM updateCourse(@PathVariable long id, @Valid @RequestBody(required = false) CourseUpdateVM courseUpdate) {
        System.out.println(courseUpdate);
        Course updated = courseService.updateCourse(id, courseUpdate);
        return new CourseVM(updated);

    }
    @DeleteMapping("/management/courses/delete/{id:[0-9]+}")
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

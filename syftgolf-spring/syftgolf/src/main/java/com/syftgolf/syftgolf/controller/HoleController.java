package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.Hole;
import com.syftgolf.syftgolf.entity.vm.hole.HoleVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.CourseRepo;
import com.syftgolf.syftgolf.repository.HoleRepo;
import com.syftgolf.syftgolf.service.HoleService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/1.0")
public class HoleController {

    /**
     * Save a new hole
     * Update a hole
     * Delete a hole
     * Get all holes for a course
     */

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    HoleRepo holeRepo;

    @Autowired
    HoleService holeService;

    @PostMapping("/management/hole/{courseId:[0-9]+}")
    GenericResponse create(@PathVariable long courseId, @RequestBody List<Hole> hole) {
        holeService.save(courseId, hole);
        return new GenericResponse("Hole saved");
    }

    @PutMapping("/management/hole/{holeId:[0-9]+}")
    GenericResponse update(@PathVariable long holeId, @RequestBody HoleVM holeVM) {
        holeService.update(holeId, holeVM);
        return new GenericResponse("Hole details updated");
    }

    @DeleteMapping("/management/hole/{holeId:[0-9]+}")
    GenericResponse delete(@PathVariable long holeId) {
        holeRepo.delete(holeRepo.getById(holeId));
        return new GenericResponse("Hole deleted");
    }

    @GetMapping("/hole/{courseId:[0-9]+}")
    List<Hole> getHoles(@PathVariable long courseId) {
       return holeService.findByCourse(courseId);
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

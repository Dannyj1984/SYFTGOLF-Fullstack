package com.syftgolf.syftgolf.course.hole;

import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HoleController {

    @Autowired
    HoleService holeService;

    @Autowired
    HoleRepository holeRepository;


    @PostMapping("/management/holes")
    @CrossOrigin
    GenericResponse createCourse(@Valid @RequestBody List<Hole> holes) {
        for(int i = 0; i < holes.size(); i++) {
            holeRepository.save(holes.get(i));
            System.out.println(holes.get(i));
        }
        return new GenericResponse("Hole saved");
    }

    @GetMapping("/getListOfHoles/{courseid}")
    @CrossOrigin
    List<Hole> holes(@PathVariable long courseid){
        return holeRepository.findAllHoles(courseid);
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

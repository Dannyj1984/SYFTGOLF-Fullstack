package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.ScoreCard;
import com.syftgolf.syftgolf.entity.vm.ScoreCardVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.ScoreCardRepo;
import com.syftgolf.syftgolf.service.ScoreCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/1.0")
public class ScoreCardController {

    /**
     * Get scorecard for an entrant
     */

    @Autowired
    ScoreCardRepo scoreCardRepo;

    @Autowired
    ScoreCardService scoreCardService;

    @GetMapping("/scorecard/{eventId:[0-9]+}/{memberId:[0-9]+}")
    public ScoreCard getScorecard(@PathVariable long eventId, @PathVariable long memberId) {
        return scoreCardService.getScoreCard(memberId, eventId);
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

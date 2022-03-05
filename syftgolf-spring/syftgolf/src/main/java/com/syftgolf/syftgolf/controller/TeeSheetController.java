package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.TeeSheet;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.EventRepo;
import com.syftgolf.syftgolf.repository.TeeSheetRepo;
import com.syftgolf.syftgolf.service.TeeSheetService;
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
@CrossOrigin
@RequestMapping("/api/1.0")
public class TeeSheetController {

    /**
     * Save new teesheet
     * Delete a teesheet
     * Get all teesheets for an event
     * Add member to a teesheet
     */

    @Autowired
    TeeSheetRepo teeSheetRepo;

    @Autowired
    TeeSheetService teeSheetService;

    @Autowired
    EventRepo eventRepo;

    @PostMapping("/management/teeSheet/{eventId:[0-9]+}")
    public GenericResponse save(@PathVariable long eventId, @RequestBody TeeSheet teeSheet) {
        return teeSheetService.save(eventId, teeSheet);
    }

    @DeleteMapping("/management/teeSheet/{teeSheetId:[0-9]+}")
    public GenericResponse delete(@PathVariable @Valid long teeSheetId)  {
            teeSheetRepo.deleteById(teeSheetId);
            return new GenericResponse("Tee time deleted");
    }

    @GetMapping("/teeSheets/{eventId:[0-9]+}")
    public List<TeeSheet> get(@PathVariable long eventId) {
        Event e = eventRepo.getEventById(eventId);
        return teeSheetRepo.findAllByEvent(e);
    }

    @PutMapping("/teeSheet/addMember/{teeSheetId:[0-9]+}/{eventId:[0-9]+}/{memberId:[0-9]+}")
    public GenericResponse addEntrant(@PathVariable long teeSheetId, @PathVariable long eventId, @PathVariable long memberId ) {
        return teeSheetService.addEntrant(teeSheetId, eventId, memberId);
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


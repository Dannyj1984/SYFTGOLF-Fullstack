package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.*;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.TournamentEntrantRepo;
import com.syftgolf.syftgolf.repository.TournamentRepo;
import com.syftgolf.syftgolf.service.TournamentEntrantService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class TournamentEntrantController {

    /**
     * Save entrant
     * Get entrants
     * Delete entrant
     */

    @Autowired
    TournamentEntrantService tournamentEntrantService;

    @Autowired
    TournamentEntrantRepo tournamentEntrantRepo;

    @Autowired
    TournamentRepo tournamentRepo;

    /**
     *
     * @param eventId id of the event
     * @param memberId id of the member
     * @return a message to say saved
     */
    @PostMapping("/tournament/entrants/{eventId:[0-9]+}/{memberId:[0-9]+}")
    GenericResponse save(@PathVariable long eventId, @PathVariable long memberId) {
        tournamentEntrantService.save(eventId, memberId);
        return new GenericResponse("Entrant saved");
    }

    /**
     *
     * @param tournamentId the event id
     * @return a list of entrants in this event
     */
    @GetMapping("/tournament/entrants/{tournamentId:[0-9]+}")
    List<TournamentEntrant> getEntrants(@PathVariable long tournamentId) {
        return tournamentEntrantService.getEntrants(tournamentId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN') || #memberId == principal.id")
    @DeleteMapping("/tournament/entrants/{memberId:[0-9]+}/{eventId:[0-9]+}")
    GenericResponse delete(@PathVariable long memberId, @PathVariable long eventId) {
        return tournamentEntrantService.deleteEntrant(memberId, eventId);
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

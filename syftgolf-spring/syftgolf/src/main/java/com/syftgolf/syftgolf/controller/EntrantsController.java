package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.Entrants;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.ScoreCard;
import com.syftgolf.syftgolf.entity.TeeSheet;
import com.syftgolf.syftgolf.entity.vm.ScoreCardVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.EntrantsRepo;
import com.syftgolf.syftgolf.repository.EventRepo;
import com.syftgolf.syftgolf.service.EntrantsService;
import com.syftgolf.syftgolf.service.ScoreCardService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class EntrantsController {

    /**
     * get entrants for an event
     * update score of an entrant
     * save a new entrant
     * delete an entrant
     * Update scorecard for entrant
     * Randomise entrants and add to teesheets
     */

    @Autowired
    EntrantsRepo entrantsRepo;

    @Autowired
    EntrantsService entrantsService;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    ScoreCardService scoreCardService;


    /**
     *
     * @param eventId the event name
     * @return a list of entrants in this event
     */
    @GetMapping("/entrants/{eventId:[0-9]+}")
    List<Entrants> getEntrants(@PathVariable long eventId) {
        Event e = eventRepo.findEventById(eventId);
        return e.getEntrants();
    }

    /**
     *
     * @param eventId the event name
     * @param memberId the id of the member to update the score of
     * @return string returned to inform of successful update
     */
    @PutMapping("/updateScore/{eventId:[0-9]+}/{memberId:[0-9]+}")
    GenericResponse updateScore(@PathVariable long eventId, @PathVariable long memberId) {
        entrantsService.updateScore(eventId, memberId);
        return new GenericResponse("Score updated");
    }

    /**
     *
     * @param eventId id of the event
     * @param memberId id of the member
     * @return a message to say saved
     */
    @PostMapping("/entrants/{eventId:[0-9]+}/{memberId:[0-9]+}")
    GenericResponse save(@PathVariable long eventId, @PathVariable long memberId) {
        return entrantsService.save(eventId, memberId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN') || #memberId == principal.id")
    @DeleteMapping("/event/entrants/{eventId:[0-9]+}/{memberId:[0-9]+}")
    @CrossOrigin
    GenericResponse delete(@PathVariable long memberId, @PathVariable long eventId) {
        return entrantsService.deleteEntrant(memberId, eventId);
    }

    /**
     *
     * @param eventId if of the event
     * @param memberId id of the members
     * @param scorecard the scorecard body to update with
     * @return a message that update successful
     */
    @CrossOrigin
    @PutMapping("/event/entrants/scorecard/{eventId:[0-9]+}/{memberId:[0-9]+}/{currentHole:[0-9]+}")
    GenericResponse updateScorecard(@PathVariable long eventId, @PathVariable long memberId, @PathVariable int currentHole, @RequestBody ScoreCardVM scorecard) {
        System.out.println(currentHole);
        scoreCardService.update(eventId, memberId, currentHole, scorecard);
        return new GenericResponse("Scorecard updated");
    }

    @PutMapping("/event/entrants/teesheet/{eventId:[0-9]+}/{perTee:[0-9]+}")
    @CrossOrigin
    List<TeeSheet> randomiseEntrants(@PathVariable long eventId, @PathVariable double perTee) {
        System.out.println("called");
        return entrantsService.randomiseEntrants(eventId, perTee);
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

package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.Tournament;
import com.syftgolf.syftgolf.entity.vm.TournamentVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.service.TournamentService;
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
@CrossOrigin
@RequestMapping("/api/1.0")
public class TournamentController {

    /**
     * save new tournament
     * get page of upcoming
     * get page of previous
     * list all
     * add event to tournament
     * remove event from a tournament
     * update tournament
     * delete tournament
     * complete a tournament
     *
     */
    @Autowired
    TournamentService tournamentService;

    @PostMapping("/management/tournament/create/{societyId:[0-9]+}")
    GenericResponse create(@PathVariable long societyId, @RequestBody @Valid Tournament tournament) {
        return tournamentService.save(societyId, tournament);
    }

    @GetMapping("/tournament/{societyId:[0-9]+}")
    public Page<Tournament> getTournaments(@PathVariable long societyId, Pageable page) {
        return tournamentService.getPageOfTournaments(societyId, page);
    }

    @GetMapping("/tournament/previous/{societyId:[0-9]+}")
    public Page<Tournament> getPreviousTournaments(@PathVariable long societyId, Pageable page) {
        return tournamentService.getPageOfPreviousTournaments(societyId, page);
    }

    @GetMapping("/tournament/list/{societyId:[0-9]+}")
    public List<Tournament> listTournaments(@PathVariable long societyId) {
        return tournamentService.listTournaments(societyId);
    }

    @PutMapping("/management/tournament/addEvent/{tournamentId:[0-9]+}/{eventId:[0-9]+}")
    GenericResponse addEvent(@PathVariable long tournamentId, @PathVariable long eventId) {
        return tournamentService.addEvent(tournamentId, eventId);
    }

    @PutMapping("/management/tournament/removeEvent/{tournamentId:[0-9]+}/{eventId:[0-9]+}")
    GenericResponse removeEvent(@PathVariable long tournamentId, @PathVariable long eventId) {
        return tournamentService.removeEvent(tournamentId, eventId);
    }

    @PutMapping("/management/tournament/update/{tournamentId:[0-9]+}")
    GenericResponse updateTournament(@PathVariable long tournamentId, @RequestBody TournamentVM tournamentVM) {
        return tournamentService.update(tournamentId, tournamentVM);
    }

    @DeleteMapping("/management/tournament/delete/{tournamentId:[0-9]+}")
    GenericResponse deleteTournament(@PathVariable long tournamentId) {
        return tournamentService.delete(tournamentId);
    }


    @PutMapping("/management/tournament/complete/{tournamentId:[0-9]+}")
    Tournament completeTournament(@PathVariable long tournamentId) {
        return tournamentService.complete(tournamentId);
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

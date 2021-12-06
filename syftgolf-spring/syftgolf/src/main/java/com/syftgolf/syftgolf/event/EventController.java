package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.event.teesheet.TeeSheet;
import com.syftgolf.syftgolf.event.teesheet.TeeSheetService;
import com.syftgolf.syftgolf.event.vm.EventUpdateVM;
import com.syftgolf.syftgolf.event.vm.EventVM;
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
import java.util.Optional;


@RestController
@RequestMapping("/api/1.0")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TeeSheetService teeSheetService;



    @GetMapping("/management/events")
    @CrossOrigin
    List<Event> getEvents() {

        return eventRepository.findAll();
    }

    //Create a new event
    @PostMapping("/management/events")
    @CrossOrigin
    GenericResponse createEvent(@Valid @RequestBody Event event) {
        //Save the current event in the database
        eventService.save(event);
        //Get the event from the database
        Event inDB = eventRepository.findByEventname(event.getEventname());
        //Create a new teesheet
        TeeSheet newTee = new TeeSheet();
        //Set the id of the teesheet to match the id of the event
        newTee.setId(inDB.getEventid());
        //Assign the teesheet to the event
        inDB.setTeeSheet(newTee);
        //Save the event
        eventService.save(inDB);
        return new GenericResponse("Event saved");
    }


    //Get a page of upcoming events
    @GetMapping("/upcomingEvents/{id:[0-9]+}")
    @CrossOrigin
    Page<EventVM> getEvents(Pageable page, @PathVariable long id) {
        return eventService.getEvents(page, id).map(EventVM::new);

    }

    //Get a page of previous events
    @GetMapping("/previousEvents/{id:[0-9]+}")
    @CrossOrigin
    Page<EventVM> getPreviousEvents(Pageable page, @PathVariable long id) {
        return eventService.getPreviousEvents(page, id).map(EventVM::new);

    }


    //Get an event by its name
    @GetMapping("/events/{eventName}")
    @CrossOrigin
    EventVM getEventByName(@PathVariable String eventName) {
        Event event = eventService.getByEventName(eventName);
        return new EventVM(event);
    }


    //Get the course details an event is being played at
    @GetMapping("/management/events/courseDetails/{id:[0-9]+}")
    @CrossOrigin
    public Map<String, ? extends Object> index(@PathVariable("id") final long id) {

        // find by eventid
        final Optional<Event> res = eventRepository.findById(id);

        //Store values from course for the current event in a map
        return res.map(e -> Map.of("id", e.getEventid(),
                "course", e.getCourse().getCourseName(),
                "course par", e.getCourse().getPar(),
                "course rating", e.getCourse().getCourseRating(),
                "course slope", e.getCourse().getSlopeRating(),
                "course postcode", e.getCourse().getPostCode()
                ))
                .orElse(Map.of());

    }


    //Edit an event
    @PutMapping("/management/events/{id:[0-9]+}")
    @CrossOrigin
    EventVM updateEvent(@PathVariable long id, @Valid @RequestBody(required = false) EventUpdateVM eventUpdate) {
        Event updated = eventService.updateEvent(id, eventUpdate);
        return new EventVM(updated);

    }

    //Update tee sheet
    @PutMapping("/management/events/teesheet/{id:[0-9]+}")
    @CrossOrigin
    GenericResponse updateTeeSheet(@PathVariable long id, @RequestBody TeeSheet teeSheetUpdate) {
        teeSheetService.updateTeeSheet(id, teeSheetUpdate);

        return new GenericResponse("teesheet updated");
    }


    //Delete an event
    @DeleteMapping("management/events/delete/{id:[0-9]+}")
    @CrossOrigin
    GenericResponse deleteEvent(@PathVariable long id) {
        eventService.deleteEvent(id);
        return new GenericResponse("Event has been removed");
    }

    //Add event entrant
    @PostMapping("event/addEntrant/{eventid:[0-9]+}/{memberid:[0-9]+}")
    @CrossOrigin
    GenericResponse createEntrant(@PathVariable long eventid, @PathVariable long memberid) {
        //Retrieve the event from database
        Event e = eventRepository.getOne(eventid);
        //Save the new entrant in the database
        eventService.saveEntrant(memberid, eventid);
        //add 1 from the current entrants count
        e.setCurrententrants(e.getCurrententrants() + 1);
        //Save event
        eventRepository.save(e);

        return new GenericResponse("You have been added to this event");
    }

    //Get event entrants
    @GetMapping("event/getEntrants/{id:[0-9]+}")
    @CrossOrigin
    List<Entrant> getEntrants(@PathVariable long id){
        return eventRepository.getEntrantDetails(id);
    }

    //Remove entrant from an event
    @DeleteMapping("event/deleteEntrants/{eventid:[0-9]+}/{memberid:[0-9]+}")
    @CrossOrigin
    GenericResponse deleteEntrant(@PathVariable long eventid, @PathVariable long memberid) {
        eventService.deleteEntrant(eventid, memberid);
        //retrieve the event from the database
        Event e = eventRepository.getOne(eventid);
        //delete 1 from the current entrants count
        e.setCurrententrants(e.getCurrententrants() - 1);
        //Save event
        eventRepository.save(e);
        return new GenericResponse("You have been removed");
    }

    //Update entrant score
    @PutMapping("event/entrant/score/{eventid:[0-9]+}/{memberid:[0-9]+}/{score:[0-9]+}")
    @CrossOrigin
    GenericResponse updateScore(@PathVariable long eventid, @PathVariable long memberid, @PathVariable int score) {
        System.out.println("score = " + score);
            eventRepository.updateScore(eventid, memberid, score);
        return new GenericResponse("Thanks for adding your score");
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

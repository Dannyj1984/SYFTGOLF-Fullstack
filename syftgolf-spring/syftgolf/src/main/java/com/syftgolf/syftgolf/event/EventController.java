package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.event.teesheet.TeeSheet;
import com.syftgolf.syftgolf.event.teesheet.TeeSheetService;
import com.syftgolf.syftgolf.event.vm.EventUpdateVM;
import com.syftgolf.syftgolf.event.vm.EventVM;
import com.syftgolf.syftgolf.event.vm.TeeSheetVM;
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
        eventService.save(event);
        Event inDB = eventRepository.findByEventname(event.getEventname());
        TeeSheet newTee = new TeeSheet();
        newTee.setId(inDB.getEventid());
        inDB.setTeeSheet(newTee);
        eventService.save(inDB);
        return new GenericResponse("Event saved");
    }


    //Get a page of events
    @GetMapping("/events")
    @CrossOrigin
    Page<EventVM> getEvents(Pageable page) {
        return eventService.getEvents(page).map(EventVM::new);

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

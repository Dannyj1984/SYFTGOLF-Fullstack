package com.syftgolf.syftgolf.controller;

import com.syftgolf.syftgolf.entity.Entrants;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.vm.event.EventUpdateVM;
import com.syftgolf.syftgolf.entity.vm.event.EventVM;
import com.syftgolf.syftgolf.error.ApiError;
import com.syftgolf.syftgolf.repository.EventRepo;
import com.syftgolf.syftgolf.service.EventService;
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

@CrossOrigin
@RestController
@RequestMapping("/api/1.0")
public class EventController {

    /**
     * Save new event
     * Page of events
     * get list of future events for adding to tournament
     * Get an event
     * Get Entrants to an event
     * Get course details
     * Delete an event
     * Update event
     * complete an event
     */

    @Autowired
    EventRepo eventRepo;

    @Autowired
    EventService eventService;

    /**
     *
     * @param societyId id of the society
     * @param courseId id of the course
     * @param event the body to be saved
     * @return a message to inform of successful save
     */
    @PostMapping("/management/event/{societyId:[0-9]+}/{courseId:[0-9]+}")
    GenericResponse save(@PathVariable long societyId, @PathVariable long courseId, @RequestBody Event event ) {
        return eventService.save(societyId, courseId, event);
    }

    //Get a page of upcoming events
    @GetMapping("/upcomingEvents/{id:[0-9]+}")
    Page<EventVM> getEvents(Pageable page, @PathVariable long id) {
        System.out.println("events");
        return eventService.getEvents(page, id).map(EventVM::new);
    }

    @GetMapping("/Events/list/{societyId:[0-9]+}")
    List<Event> listOfEvents(@PathVariable long societyId){
        return eventService.getEventsList(societyId);
    }

    @GetMapping("/previousEvents/list/{societyId:[0-9]+}")
    List<Event> listOfPreviousEvents(@PathVariable long societyId){
        return eventService.getPreviousEventsList(societyId);
    }

    //Get a single event
    //Get an event by its name
    @GetMapping("/events/{eventName}")
    @CrossOrigin
    EventVM getEventByName(@PathVariable String eventName) {
        Event event = eventService.getByEventName(eventName);
        return new EventVM(event);
    }

    //Get a page of previous events
    @GetMapping("/previousEvents/{id:[0-9]+}")
    Page<EventVM> getPreviousEvents(@PathVariable long id, Pageable page) {
        return eventService.getPreviousEvents(page, id).map(EventVM::new);

    }

    /**
     *
     * @param eventId id of the event
     * @return a List of members to an event
     */
    @GetMapping("/event/entrants/{eventId:[0-9]+}")
    List<Member> getEntrants(@PathVariable long eventId) {
        return eventService.getEntrants(eventId);
    }

    @GetMapping("/management/events/courseDetails/{id:[0-9]+}")
    @CrossOrigin
    public Map<String, ? extends Object> index(@PathVariable("id") final long id) {

        // find by eventid
        final Optional<Event> res = eventRepo.findById(id);

        //Store values from course for the current event in a map
        return res.map(e -> Map.of("id", e.getId(),
                "courseId", e.getCourse().getId(),
                "course", e.getCourse().getName(),
                "coursePar", e.getCourse().getPar(),
                "courseRating", e.getCourse().getCourseRating(),
                "courseSlope", e.getCourse().getSlopeRating()
        ))
                .orElse(Map.of());

    }

    /**
     *
     * @param eventId id of the event
     * @return a message to confirm deletion
     */
    @DeleteMapping("/management/event/{eventId:[0-9]+}")
    @CrossOrigin
    GenericResponse delete(@PathVariable long eventId) throws IllegalArgumentException {
        try {
            eventRepo.delete(eventRepo.findEventById(eventId));
        } catch(IllegalArgumentException error) {
            System.out.println(error);
            return new GenericResponse("Event not found");
        }
        return new GenericResponse("Event deleted");
    }

    @PutMapping("/management/event/{eventId:[0-9]+}")
    @CrossOrigin
    GenericResponse updateEvent(@PathVariable long eventId, @Valid @RequestBody EventUpdateVM eventUpdateVM) {
        System.out.println(eventUpdateVM);
        return eventService.update(eventId, eventUpdateVM);
    }

    @PutMapping("/management/complete/{eventId:[0-9]+}")
    GenericResponse completeEvent(@PathVariable long eventId){
        return eventService.complete(eventId);
    }

    @GetMapping("/event/test/{eventId:[0-9]+}")
    List<Entrants> eventTest(@PathVariable long eventId) {
        return eventService.updateSyftCupScores(eventId);
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

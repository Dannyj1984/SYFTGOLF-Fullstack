package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.event.vm.EventUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class EventService {


    EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Page<Event> getEvents(Pageable pageable ) {

        return eventRepository.findAll(pageable);
    }

    public Event getByEventName(String eventname) {
        Event inDB = eventRepository.findByEventname(eventname);
        if(inDB == null) {
            throw new NotFoundException(eventname + " not found");
        }
        return inDB;
    }

    public Event updateEvent(long id, EventUpdateVM eventUpdate) {
        Event inDB = eventRepository.getOne(id);
        inDB.setEventname(eventUpdate.getEventname());
        return eventRepository.save(inDB);
    }

    public void deleteEvent(long id) {
        Event event = eventRepository.getOne(id);
        eventRepository.deleteById(id);

    }

}

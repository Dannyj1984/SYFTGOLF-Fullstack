package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.event.teesheet.TeeSheet;
import com.syftgolf.syftgolf.event.teesheet.TeeSheetRepository;
import com.syftgolf.syftgolf.event.vm.EventUpdateVM;
import com.syftgolf.syftgolf.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService {


    EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    //Save new event
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    //Get a page of events
    public Page<Event> getEvents(Pageable pageable, long id) {

        return eventRepository.findAllByDate(pageable, id);
    }


    //Get a page of previous events
    public Page<Event> getPreviousEvents(Pageable pageable, long id) {

        return eventRepository.findAllBeforeByDate(pageable, id);
    }

    //Get an event by the event name
    public Event getByEventName(String eventname) {
        Event inDB = eventRepository.findByEventname(eventname);
        if(inDB == null) {
            throw new NotFoundException(eventname + " not found");
        }
        return inDB;
    }

    //Update and event
    public Event updateEvent(long id, EventUpdateVM eventUpdate) {
        Event inDB = eventRepository.getOne(id);
        inDB.setEventname(eventUpdate.getEventname());
        inDB.setDate(eventUpdate.getDate());
        inDB.setCost(eventUpdate.getCost());
        inDB.setMaxentrants(eventUpdate.getMaxentrants());
        inDB.setEventtype(eventUpdate.getEventtype());
        inDB.setQualifier(eventUpdate.getQualifier());
        inDB.setInfo(eventUpdate.getInfo());
        inDB.setWinner(eventUpdate.getWinner());
        return eventRepository.save(inDB);
    }


    //Delete an event
    public void deleteEvent(long id) {
        Event event = eventRepository.getOne(id);
        eventRepository.deleteById(id);
    }

    //Save entrant
    public void saveEntrant(long memberid, long eventid) {
         eventRepository.createEntrants(memberid, eventid);
    }

    //Delete an Entrant
    public void deleteEntrant(long eventid, long memberid) {
        eventRepository.deleteEntrantByIds(eventid, memberid);
    }



}

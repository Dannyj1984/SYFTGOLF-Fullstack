package com.syftgolf.syftgolf.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    //return Event with this eventname
    Event findByEventname(String eventName);

    public Event findByEventid(int eventid);









}

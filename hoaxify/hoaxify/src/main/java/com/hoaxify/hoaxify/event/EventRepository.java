package com.hoaxify.hoaxify.event;

import com.hoaxify.hoaxify.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    //return Event with this eventname
    Event findByEventname(String eventName);

    public Event findByEventid(int eventid);









}

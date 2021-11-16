package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    //return Event with this eventname
    Event findByEventname(String eventName);

    Event findByEventid(int eventid);







}

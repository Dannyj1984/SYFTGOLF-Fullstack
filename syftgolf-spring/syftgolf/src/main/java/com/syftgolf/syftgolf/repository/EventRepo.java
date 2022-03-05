package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Society;
import com.syftgolf.syftgolf.entity.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    Event findEventByName(String name);

    Event findEventById(long id);

    List<Event> findAllBySocietyId(long id);

    List<Event> findAllByTournament(Tournament tournament);

    Event getEventById(long eventId);

    @Query(value = "select * from Event where event.date >= current_date AND event.society_id=:id", nativeQuery = true)
    Page<Event> findAllByDate(Pageable pageable, long id);

    @Query(value = "select * from Event where event.date < current_date AND event.society_id=:id", nativeQuery = true)
    Page<Event> findAllByDateBefore(Pageable pageable, long id);

    Page<Event> findAllEventBySocietyIdAndDateIsBefore(Pageable pageable, long id, LocalDate today);
}

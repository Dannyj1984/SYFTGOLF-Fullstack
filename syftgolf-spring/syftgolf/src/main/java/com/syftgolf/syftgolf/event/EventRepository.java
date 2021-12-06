package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    //return Event with this eventname
    Event findByEventname(String eventName);

    //Return an event by its id
    Event findByEventid(int eventid);

    //Find all events for a society after or equal to todays date
    @Query(value = "select * from Event where event.date >= current_date AND event.society_id=:id", nativeQuery = true)
    Page<Event> findAllByDate(Pageable pageable, long id);

    //Find all events for a society prior to todays date
    @Query(value = "select * from Event where event.date < current_date AND event.society_id=:id", nativeQuery = true)
    Page<Event> findAllBeforeByDate(Pageable pageable, long id);


    //Find all events for a society
    public Page<Event> findAllBySocietyId(Pageable page, long id);

    //Get event entrants name, handicap and score
    @Query(value = "SELECT member.username, member.firstname, member.surname, member.handicap, entrants.score FROM member INNER JOIN entrants ON member.userid=entrants.member_id WHERE entrants.event_id=:eventid", nativeQuery = true)
    List<Entrant> getEntrantDetails(long eventid);

    //Add new entrant
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO entrants VALUES (:memberid, :eventid, '0')", nativeQuery = true)
    void createEntrants(long memberid, long eventid);

    //Delete an entrant
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM entrants WHERE member_id=:memberid AND event_id=:eventid", nativeQuery = true)
    void deleteEntrantByIds(long eventid, long memberid);

    //Update an entrant's score
    @Transactional
    @Modifying
    @Query(value = "UPDATE entrants SET score = :score WHERE member_id = :memberid AND event_id = :eventid ", nativeQuery = true)
    void updateScore(long eventid, long memberid, int score);





}

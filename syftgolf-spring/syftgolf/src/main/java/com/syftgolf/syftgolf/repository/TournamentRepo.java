package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TournamentRepo extends JpaRepository<Tournament, Long> {

    Tournament findMultiEventByName(String name);

    Tournament findTournamentById(long tournamentId);

    @Query(value = "select * from Tournament where tournament.end_date >= current_date AND tournament.society_id=:societyId", nativeQuery = true)
    Page<Tournament> findAllByDate(long societyId, Pageable page);

    @Query(value = "select * from Tournament where tournament.end_date < current_date AND tournament.society_id=:societyId", nativeQuery = true)
    Page<Tournament> findAllByDateBefore(Pageable pageable, long societyId);

    List<Tournament> findAllBySocietyIdOrderByNameAsc(long societyId);

    Tournament findTournamentByName(String tournamentName);
}

package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepo extends JpaRepository<Tournament, Long> {

    Tournament findMultiEventByName(String name);

    Tournament findTournamentById(long tournamentId);
}

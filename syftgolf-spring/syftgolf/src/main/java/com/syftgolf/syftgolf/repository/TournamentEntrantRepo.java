package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.Tournament;
import com.syftgolf.syftgolf.entity.TournamentEntrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TournamentEntrantRepo extends JpaRepository<TournamentEntrant, Long> {

    List<TournamentEntrant> findAllByTournamentOrderByTotalScoreAsc(Tournament event);

    List<TournamentEntrant> findAllByTournamentOrderByTotalScoreDesc(Tournament event);

    @Transactional
    @Modifying
    void deleteByMemberAndTournament(Member m, Tournament me);
}

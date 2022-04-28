package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.MatchPlayer;
import com.syftgolf.syftgolf.entity.Matchplay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchPlayerRepo extends JpaRepository<MatchPlayer, Long> {

    List<MatchPlayer> findAllByMatchplay(Matchplay matchplay);
}

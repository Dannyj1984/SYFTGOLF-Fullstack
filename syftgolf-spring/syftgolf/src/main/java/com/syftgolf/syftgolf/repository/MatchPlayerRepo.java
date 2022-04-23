package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.MatchPlayer;
import com.syftgolf.syftgolf.entity.Matchplay;
import com.syftgolf.syftgolf.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MatchPlayerRepo extends JpaRepository<MatchPlayer, Long> {

    List<MatchPlayer> findAllByMatchplay(Matchplay matchplay);
}

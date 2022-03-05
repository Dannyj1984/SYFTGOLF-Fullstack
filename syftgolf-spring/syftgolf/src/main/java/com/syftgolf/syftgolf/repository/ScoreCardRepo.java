package com.syftgolf.syftgolf.repository;

import com.syftgolf.syftgolf.entity.Entrants;
import com.syftgolf.syftgolf.entity.ScoreCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreCardRepo extends JpaRepository<ScoreCard, Long> {

    ScoreCard findScoreCardByEntrants(Entrants en);
}

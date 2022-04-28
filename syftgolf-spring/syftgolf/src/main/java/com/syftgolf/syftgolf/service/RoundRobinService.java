package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.MatchPlayer;
import com.syftgolf.syftgolf.entity.Matchplay;
import com.syftgolf.syftgolf.entity.RoundRobin;
import com.syftgolf.syftgolf.entity.vm.matchplay.RoundRobinVM;
import com.syftgolf.syftgolf.repository.MatchPlayRepo;
import com.syftgolf.syftgolf.repository.MatchPlayerRepo;
import com.syftgolf.syftgolf.repository.RoundRobinRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundRobinService {
    RoundRobinRepo roundRobinRepo;
    MatchPlayRepo matchPlayRepo;
    MatchPlayerRepo matchPlayerRepo;

    public RoundRobinService(RoundRobinRepo roundRobinRepo, MatchPlayerRepo matchPlayerRepo, MatchPlayRepo matchPlayRepo) {
        this.roundRobinRepo = roundRobinRepo;
        this.matchPlayRepo = matchPlayRepo;
        this.matchPlayerRepo = matchPlayerRepo;
    }

    public List<RoundRobin> getMatches(long matchplayId) {
        Matchplay mp = matchPlayRepo.findMatchplayById(matchplayId);
        List<RoundRobin> rr = roundRobinRepo.findAllByMatchplay(mp);
        return rr;
    }

    public void updateScores(long roundRobinId, long MatchPlayId, int p1Score, int p2Score) {
        //Set the scores in the round robin for p1 and p2
        roundRobinRepo.findById(roundRobinId).ifPresent(roundRobin -> {
            roundRobin.setP1Score(p1Score);
            roundRobin.setP2Score(p2Score);
            roundRobinRepo.save(roundRobin);
        });
        //Get the players from this match and set their scores and points accordingly
        RoundRobin round = roundRobinRepo.findById(roundRobinId).get();
        List<MatchPlayer> players = round.getPlayers();
        MatchPlayer mp1 = players.get(0);
        MatchPlayer mp2 = players.get(1);
        if(p1Score > p2Score) {
            mp1.setScore(players.get(0).getScore() + p1Score);
            mp2.setScore(players.get(1).getScore() - p1Score);
            mp1.setPoints(players.get(0).getPoints() + 3);
        }
        if(p2Score > p1Score) {
            mp2.setScore(players.get(1).getScore() + p2Score);
            mp1.setScore(players.get(0).getScore() - p2Score);
            mp2.setPoints(players.get(1).getPoints() + 3);
        }
        if(p1Score == p2Score) {
            mp1.setPoints(players.get(0).getPoints() + 1);
            mp2.setPoints(players.get(1).getPoints() + 1);
        }
        matchPlayerRepo.save(mp2);
        matchPlayerRepo.save(mp1);


    }
}

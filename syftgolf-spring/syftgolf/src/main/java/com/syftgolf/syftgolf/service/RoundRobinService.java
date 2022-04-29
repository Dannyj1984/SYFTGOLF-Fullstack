package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.MatchPlayer;
import com.syftgolf.syftgolf.entity.Matchplay;
import com.syftgolf.syftgolf.entity.RoundRobin;
import com.syftgolf.syftgolf.entity.vm.matchplay.RoundRobinVM;
import com.syftgolf.syftgolf.repository.MatchPlayRepo;
import com.syftgolf.syftgolf.repository.MatchPlayerRepo;
import com.syftgolf.syftgolf.repository.RoundRobinRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void updateSemiScores(long matchPlayId, String playerOne, String playerTwo, int p1Score, int p2Score) {
        System.out.println(playerTwo);
        //Get the matchplay
        Matchplay mp = matchPlayRepo.findMatchplayById(matchPlayId);

        //Get the players from this match and set their scores and points accordingly
        List<MatchPlayer> players =  mp.getSemiFinalists();

        //Get list of finalists
        List<MatchPlayer> finalistList = mp.getFinalists();

        for(int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getMember().getUsername());
            //If the current player is player 1
            if(players.get(i).getMember().getUsername().equals(playerOne)) {
                //Set their score as the score for player 1
                players.get(i).setSfScore(p1Score);
                matchPlayerRepo.save(players.get(i));

                //If p1 score if highest
                if(p1Score > p2Score) {
                    //Add the current player to the finalist list
                    finalistList.add(players.get(i));
                    //Save the updated finalist list
                    mp.setFinalists(finalistList);
                    matchPlayRepo.save(mp);
                }
            }

            //If the current player is player 2
            if(players.get(i).getMember().getUsername().equals(playerTwo)) {
                //Set their score as the score for player 2
                players.get(i).setSfScore(p2Score);
                matchPlayerRepo.save(players.get(i));
                if(p2Score > p1Score) {
                    //Add the current player to the finalist list
                    finalistList.add(players.get(i));
                    //Save the updated finalist list
                    mp.setFinalists(finalistList);
                    matchPlayRepo.save(mp);
                }
            }
        }
    }

    public void updateFinalScores(long matchPlayId, String playerOne, String playerTwo, int p1Score, int p2Score) {
        //Get the matchplay
        Matchplay mp = matchPlayRepo.findMatchplayById(matchPlayId);
        //Get the players from this match and set their scores and points accordingly
        List<MatchPlayer> players = mp.getFinalists();
        for(MatchPlayer mps : players) {

            if(mps.getMember().getUsername().equals(playerOne)) {
                mps.setFScore(p1Score);
                matchPlayerRepo.save(mps);
                if(p1Score > p2Score) {
                    mp.setWinner(mps.getMember().getFirstName() + " " + mps.getMember().getSurname());
                    matchPlayRepo.save(mp);
                }
            }
            if(mps.getMember().getUsername().equals(playerTwo)) {
                mps.setFScore(p2Score);
                matchPlayerRepo.save(mps);
                if(p2Score > p1Score) {
                    mp.setWinner(mps.getMember().getFirstName() + " " + mps.getMember().getSurname());
                    matchPlayRepo.save(mp);
                }
            }
        }
    }
}

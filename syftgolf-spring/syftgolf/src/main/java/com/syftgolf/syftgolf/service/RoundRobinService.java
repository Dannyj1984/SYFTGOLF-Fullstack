package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.Matchplay;
import com.syftgolf.syftgolf.entity.RoundRobin;
import com.syftgolf.syftgolf.repository.MatchPlayRepo;
import com.syftgolf.syftgolf.repository.RoundRobinRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundRobinService {
    RoundRobinRepo roundRobinRepo;
    MatchPlayRepo matchPlayRepo;

    public RoundRobinService(RoundRobinRepo roundRobinRepo, MatchPlayRepo matchPlayRepo) {
        this.roundRobinRepo = roundRobinRepo;
        this.matchPlayRepo = matchPlayRepo;
    }

    public List<RoundRobin> getMatches(long matchplayId) {
        Matchplay mp = matchPlayRepo.findMatchplayById(matchplayId);
        List<RoundRobin> rr = roundRobinRepo.findAllByMatchplay(mp);
        System.out.println(rr.get(0));
        return rr;
    }
}

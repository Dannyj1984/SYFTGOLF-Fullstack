package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.MatchPlayer;
import com.syftgolf.syftgolf.entity.Matchplay;
import com.syftgolf.syftgolf.repository.MatchPlayRepo;
import com.syftgolf.syftgolf.repository.MatchPlayerRepo;
import com.syftgolf.syftgolf.repository.MemberRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchPlayerService {

    MatchPlayerRepo matchPlayerRepo;

    MatchPlayRepo matchPlayRepo;

    MemberRepo memberRepo;

    public MatchPlayerService(MatchPlayerRepo matchPlayerRepo, MemberRepo memberRepo, MatchPlayRepo matchPlayRepo) {
        this.matchPlayerRepo = matchPlayerRepo;
        this.matchPlayRepo = matchPlayRepo;
        this.memberRepo = memberRepo;
    }

    public List<MatchPlayer> enter(long memberId, long matchplayId) {
        MatchPlayer mp = new MatchPlayer(memberRepo.findMemberById(memberId), matchPlayRepo.findMatchplayById(matchplayId));
        matchPlayerRepo.save(mp);

        //save matchplayer to list for this matchplay event
        matchPlayRepo.findById(matchplayId).ifPresent(matchplay -> { //If the matchplay is found
            List<MatchPlayer> list = matchplay.getMatchPlayer();     //get a list of current players
            list.add(mp);                                            //add the new matchplayer to the list
            matchplay.setMatchPlayer(list);                          // update the matchplayer list
            matchPlayRepo.save(matchplay);                           //save
        });

        return matchPlayRepo.findMatchplayById(matchplayId).getMatchPlayer();
    }
}

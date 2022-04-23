package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.*;
import com.syftgolf.syftgolf.repository.*;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchPlayService {
    /**
     * Get list of entrants to an event
     * save an event
     * Get a page of upcoming events
     * Get a page of previous events
     * Update an event
     * Complete an event
     */

    MatchPlayRepo matchPlayRepo;

    MatchPlayerRepo matchPlayerRepo;

    EventRepo eventRepo;

    SocietyRepo societyRepo;

    CourseRepo courseRepo;

    MemberRepo memberRepo;

    EntrantsRepo entrantsRepo;

    TournamentRepo tournamentRepo;

    TournamentEntrantRepo tournamentEntrantRepo;

    RoundRobinRepo roundRobinRepo;

    public MatchPlayService(MatchPlayRepo matchPlayRepo, RoundRobinRepo roundRobinRepo, MatchPlayerRepo matchPlayerRepo, EventRepo eventRepo, TournamentRepo tournamentRepo, TournamentEntrantRepo tournamentEntrantRepo, EntrantsRepo entrantsRepo, SocietyRepo societyRepo, CourseRepo courseRepo, MemberRepo memberRepo) {
        this.matchPlayerRepo = matchPlayerRepo;
        this.matchPlayRepo =matchPlayRepo;
        this.eventRepo = eventRepo;
        this.entrantsRepo = entrantsRepo;
        this.societyRepo = societyRepo;
        this.courseRepo = courseRepo;
        this.memberRepo = memberRepo;
        this.tournamentRepo = tournamentRepo;
        this.tournamentEntrantRepo = tournamentEntrantRepo;
        this.roundRobinRepo = roundRobinRepo;
    }

    public GenericResponse save(long societyId, Matchplay matchplay) {
        Matchplay mp = new Matchplay(matchplay);
        societyRepo.findById(societyId)
                .ifPresent(society -> {
                    List<Matchplay> lmps = society.getMatchplay();
                    lmps.add(mp);
                    society.setMatchplay(lmps);
                    mp.setSociety(society);
                    matchPlayRepo.save(mp);
                    societyRepo.save(society);
                });
        return new GenericResponse("Matchplay saved");
    }

    public Matchplay update(long matchplayid, Matchplay matchplay) {
        matchPlayRepo.findById(matchplayid)
                .ifPresent(mp -> {
                    mp.setName(matchplay.getName());
                    mp.setYear(matchplay.getYear());
                    matchPlayRepo.save(mp);
                });
        return matchPlayRepo.getById(matchplayid);
    }

    public List<MatchPlayer> get(long matchplayId) {
        Matchplay mp = matchPlayRepo.findMatchplayById(matchplayId);
        return matchPlayerRepo.findAllByMatchplay(mp);
    }

    public Matchplay groups(long matchplayId) {
        Matchplay mp = matchPlayRepo.findMatchplayById(matchplayId);
        List<MatchPlayer> list = new ArrayList<>(mp.getMatchPlayer());
        Collections.shuffle(list); //Shuffle the list
        //Assign each member to a group, 1, 2, 3 so there are 4 members per group
        for(int i = 0; i < list.size(); i = i + 3) {
                list.get(i).setGrouping(1);
                list.get(i+1).setGrouping(2);
                list.get(i+2).setGrouping(3);
                matchPlayerRepo.save(list.get(i));
                matchPlayerRepo.save(list.get(i+1));
                matchPlayerRepo.save(list.get(i+2));
            }
        createRoundRobin(matchplayId);

        return matchPlayRepo.findById(matchplayId).get();
    }

    public Page<Matchplay> getMatchPlays(Pageable pageable) {
        return matchPlayRepo.findAll(pageable);
    }

    public void createRoundRobin(long matchPlayId) {
        Matchplay match = matchPlayRepo.findMatchplayById(matchPlayId);
        List<MatchPlayer> players = match.getMatchPlayer();
        List<MatchPlayer> group1 = new ArrayList<>();
        List<MatchPlayer> group2 = new ArrayList<>();
        List<MatchPlayer> group3 = new ArrayList<>();

        System.out.println(players.size());

        for(MatchPlayer mp : players) {
            if(mp.getGrouping() == 1) {
                group1.add(mp);
            }
            if(mp.getGrouping() == 2) {
                group2.add(mp);
            }
            if(mp.getGrouping() == 3) {
                group3.add(mp);
            }
        }
        Collections.shuffle(group1);
        Collections.shuffle(group2);
        Collections.shuffle(group3);

        List<RoundRobin> rrList = new ArrayList<>();

        List<MatchPlayer> group1m1 = Arrays.asList(group1.get(0), group1.get(1));
        Collections.shuffle(group1m1);
        List<MatchPlayer> group1m2 = Arrays.asList(group1.get(2), group1.get(3));
        Collections.shuffle(group1m2);
        List<MatchPlayer> group1m3 = Arrays.asList(group1.get(0), group1.get(2));
        Collections.shuffle(group1m3);
        List<MatchPlayer> group1m4 = Arrays.asList(group1.get(1), group1.get(3));
        Collections.shuffle(group1m4);
        List<MatchPlayer> group1m5 = Arrays.asList(group1.get(0), group1.get(3));
        Collections.shuffle(group1m5);
        List<MatchPlayer> group1m6 = Arrays.asList(group1.get(1), group1.get(2));
        Collections.shuffle(group1m6);

        List<MatchPlayer> group2m1 = Arrays.asList(group2.get(0), group2.get(1));
        Collections.shuffle(group2m1);
        List<MatchPlayer> group2m2 = Arrays.asList(group2.get(2), group2.get(3));
        Collections.shuffle(group2m2);
        List<MatchPlayer> group2m3 = Arrays.asList(group2.get(0), group2.get(2));
        Collections.shuffle(group2m3);
        List<MatchPlayer> group2m4 = Arrays.asList(group2.get(1), group2.get(3));
        Collections.shuffle(group2m4);
        List<MatchPlayer> group2m5 = Arrays.asList(group2.get(0), group2.get(3));
        Collections.shuffle(group2m5);
        List<MatchPlayer> group2m6 = Arrays.asList(group2.get(1), group2.get(2));
        Collections.shuffle(group2m6);

        List<MatchPlayer> group3m1 = Arrays.asList(group3.get(0), group3.get(1));
        Collections.shuffle(group3m1);
        List<MatchPlayer> group3m2 = Arrays.asList(group3.get(2), group3.get(3));
        Collections.shuffle(group3m2);
        List<MatchPlayer> group3m3 = Arrays.asList(group3.get(0), group3.get(2));
        Collections.shuffle(group3m3);
        List<MatchPlayer> group3m4 = Arrays.asList(group3.get(1), group3.get(3));
        Collections.shuffle(group3m4);
        List<MatchPlayer> group3m5 = Arrays.asList(group3.get(0), group3.get(3));
        Collections.shuffle(group3m5);
        List<MatchPlayer> group3m6 = Arrays.asList(group3.get(1), group3.get(2));
        Collections.shuffle(group3m6);

        RoundRobin g1M1 = new RoundRobin(group1m1, 1);
        RoundRobin g1M2 = new RoundRobin(group1m2, 1);
        RoundRobin g1M3 = new RoundRobin(group1m3, 2);
        RoundRobin g1M4 = new RoundRobin(group1m4, 2);
        RoundRobin g1M5 = new RoundRobin(group1m5, 3);
        RoundRobin g1M6 = new RoundRobin(group1m6, 3);
        RoundRobin g2M1 = new RoundRobin(group2m1, 1);
        RoundRobin g2M2 = new RoundRobin(group2m2, 1);
        RoundRobin g2M3 = new RoundRobin(group2m3, 2);
        RoundRobin g2M4 = new RoundRobin(group2m4, 2);
        RoundRobin g2M5 = new RoundRobin(group2m5, 3);
        RoundRobin g2M6 = new RoundRobin(group2m6, 3);
        RoundRobin g3M1 = new RoundRobin(group3m1, 1);
        RoundRobin g3M2 = new RoundRobin(group3m2, 1);
        RoundRobin g3M3 = new RoundRobin(group3m3, 2);
        RoundRobin g3M4 = new RoundRobin(group3m4, 2);
        RoundRobin g3M5 = new RoundRobin(group3m5, 3);
        RoundRobin g3M6 = new RoundRobin(group3m6, 3);

        rrList.add(g1M1);
        rrList.add(g1M2);
        rrList.add(g1M3);
        rrList.add(g1M4);
        rrList.add(g1M5);
        rrList.add(g1M6);
        rrList.add(g2M1);
        rrList.add(g2M2);
        rrList.add(g2M3);
        rrList.add(g2M4);
        rrList.add(g2M5);
        rrList.add(g2M6);
        rrList.add(g3M1);
        rrList.add(g3M2);
        rrList.add(g3M3);
        rrList.add(g3M4);
        rrList.add(g3M5);
        rrList.add(g3M6);

        //set the matchplay for the round to the current matchplay
        for(RoundRobin rr : rrList) {
            rr.setMatchplay(match);
        }
        //add all the rounds to the list of rounds in this matchplay
        match.setRoundrobin(rrList);

        roundRobinRepo.saveAll(rrList);

    }
}

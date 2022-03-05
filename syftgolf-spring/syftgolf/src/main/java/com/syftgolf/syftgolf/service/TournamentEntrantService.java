package com.syftgolf.syftgolf.service;


import com.syftgolf.syftgolf.entity.*;
import com.syftgolf.syftgolf.repository.EntrantsRepo;
import com.syftgolf.syftgolf.repository.MemberRepo;
import com.syftgolf.syftgolf.repository.TournamentEntrantRepo;
import com.syftgolf.syftgolf.repository.TournamentRepo;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TournamentEntrantService {

        /**
         * Save new entrant to a multievent
         * Get List of entrants (sort total score whilst retrieving asc for medal and desc for stableford.
         * Delete entrant from multievent
         */

        TournamentEntrantRepo tournamentEntrantRepo;

        TournamentRepo tournamentRepo;

        MemberRepo memberRepo;

        EntrantsRepo entrantsRepo;

        public TournamentEntrantService(EntrantsRepo entrantsRepo, TournamentEntrantRepo multiEventEntrantsRepo, TournamentRepo tournamentRepo, MemberRepo memberRepo) {
            this.memberRepo = memberRepo;
            this.tournamentEntrantRepo = multiEventEntrantsRepo;
            this.tournamentRepo = tournamentRepo;
            this.entrantsRepo = entrantsRepo;
        }

        /**
         *
         * @param eventId name of the event
         * @param memberId id of the member to enter
         */
        public void save(long eventId, long memberId) {
            Tournament e = tournamentRepo.findTournamentById(eventId);
            Member m = memberRepo.findMemberById(memberId);
            TournamentEntrant en = new TournamentEntrant(m, e, 0);
            //Update the current entrants entered in an event by 1
            e.setNoOfEntrants(e.getNoOfEntrants() + 1);
            tournamentRepo.save(e);
            //Get the current list of entrants for this event
            List<TournamentEntrant> entrants = e.getTournamentEntrants();
            //Add the current entrant to the list of entrants for this event.
            entrants.add(en);
            tournamentEntrantRepo.save(en);
        }

        /**
         * @param event the event name
         */
        public List<TournamentEntrant> getEntrants(String event) {
            List<TournamentEntrant> sortedEntrants = new ArrayList<>();
            //Get current tournament
            Tournament me = tournamentRepo.findMultiEventByName(event);
            if(me.getType().equals("Stableford")) {
                //Get all entrants to this tournament
                List<TournamentEntrant> multiEntrants = me.getTournamentEntrants();
                //Get all events included in this tournament
                List<Event> events = me.getEvents();
                //Reset totalScore to 0 before calculating total scores for all event.
                for (TournamentEntrant ment : multiEntrants) {
                    //For each event
                    ment.setTotalScore(0);
                    tournamentEntrantRepo.save(ment);
                }
                //Add up scores for each tournamentEntrant for each event.
                for (TournamentEntrant ment : multiEntrants) {
                    //For each event
                    for (Event e : events) {
                        List<Entrants> en = e.getEntrants();
                        for (Entrants ent : en) {
                            if (ment.getMember().getUsername().equals(ent.getMember().getUsername())) {
                                ment.setTotalScore(ment.getTotalScore() + ent.getScore());
                            }
                        }
                    }
                    tournamentEntrantRepo.save(ment);
                }
                //
                sortedEntrants = tournamentEntrantRepo.findAllByTournamentOrderByTotalScoreDesc(me);
            } else if (me.getType().equals("Medal")) {
                //Get all entrants to this tournament
                List<TournamentEntrant> multiEntrants = me.getTournamentEntrants();
                //Get all events included in this tournament
                List<Event> events = me.getEvents();
                //Reset totalScore to 0 before calculating total scores for all event.
                for (TournamentEntrant ment : multiEntrants) {
                    //For each event
                    ment.setTotalScore(0);
                    tournamentEntrantRepo.save(ment);
                }
                //Add up scores for each tournamentEntrant for each event.
                for (TournamentEntrant ment : multiEntrants) {
                    //For each event
                    for (Event e : events) {
                        List<Entrants> en = e.getEntrants();
                        for (Entrants ent : en) {
                            if (ment.getMember().getUsername().equals(ent.getMember().getUsername())) {
                                ment.setTotalScore(ment.getTotalScore() + ent.getScore());
                            }
                        }
                    }
                    tournamentEntrantRepo.save(ment);
                }
                //
                sortedEntrants = tournamentEntrantRepo.findAllByTournamentOrderByTotalScoreAsc(me);
            }
            return sortedEntrants;
        }

    /**
     *
     * @param eventId id of the event
     * @param memberId id of the member
     * @return a message to confirm removal
     */
        public GenericResponse deleteEntrant(long memberId, long eventId) {
            Tournament t = tournamentRepo.getById(eventId);
            Member m = memberRepo.getById(memberId);
            t.setNoOfEntrants(t.getNoOfEntrants() -1);
            tournamentRepo.save(t);
            tournamentEntrantRepo.deleteByMemberAndTournament(m, t);
            return new GenericResponse("Entrant removed");
        }




    }

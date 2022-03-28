package com.syftgolf.syftgolf.service;


import com.syftgolf.syftgolf.entity.*;
import com.syftgolf.syftgolf.repository.EntrantsRepo;
import com.syftgolf.syftgolf.repository.MemberRepo;
import com.syftgolf.syftgolf.repository.TournamentEntrantRepo;
import com.syftgolf.syftgolf.repository.TournamentRepo;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
        public GenericResponse save(long eventId, long memberId) {
            GenericResponse gr = new GenericResponse("This member is already entered in this event");
            Tournament t = tournamentRepo.findTournamentById(eventId);
            Member m = memberRepo.findMemberById(memberId);
            TournamentEntrant en = new TournamentEntrant(m, t);

            //Update the current entrants entered in a tournament by 1
            t.setNoOfEntrants(t.getNoOfEntrants() + 1);
            tournamentRepo.save(t);
            boolean entered = false;
            //Get the current list of entrants for this event
            List<TournamentEntrant> entrants = t.getTournamentEntrants();

            //Check if member is already entered in this event
            for (TournamentEntrant entrants1 : entrants) {
                System.out.println(entrants1.getMember().getUsername());
                System.out.println(m.getUsername());
                if (entrants1.getMember().getUsername().equals(m.getUsername())) {
                    entered = true;
                    break;
                }
            }
            if (!entered) {
                tournamentEntrantRepo.save(en);
                entrants.add(en);
                t.setTournamentEntrants(entrants);
                tournamentRepo.save(t);
                gr = new GenericResponse("Entered");
            }

            return gr;
        }

        /**
         * @param tournamentId the event name
         * return a list of sorted entrants depending on event type
         */
        public List<TournamentEntrant> getEntrants(long tournamentId) {
            List<TournamentEntrant> sortedEntrants = new ArrayList<>();
            //Get current tournament
            Tournament t = tournamentRepo.findTournamentById(tournamentId);
            if(t.getType().equals("Stableford")) {
                //Get all entrants to this tournament
                List<TournamentEntrant> tournamenEntrants = t.getTournamentEntrants();
                //Get all events included in this tournament
                List<Event> events = t.getEvents();
                //Reset totalScore to 0 before calculating total scores for all event.
                for (TournamentEntrant ment : tournamenEntrants) {
                    //For each event
                    ment.setTotalScore(0);
                    tournamentEntrantRepo.save(ment);
                }
                //Add up scores for each tournamentEntrant for each event.
                for (TournamentEntrant ment : tournamenEntrants) {
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
                sortedEntrants = tournamentEntrantRepo.findAllByTournamentOrderByTotalScoreDesc(t);
            }
            else if (t.getType().equals("Medal")) {
                //Get all entrants to this tournament
                List<TournamentEntrant> tournamentEntrants = t.getTournamentEntrants();
                //Get all events included in this tournament
                List<Event> events = t.getEvents();
                //Reset totalScore to 0 before calculating total scores for all event.
                for (TournamentEntrant ment : tournamentEntrants) {
                    //For each event
                    ment.setTotalScore(0);
                    tournamentEntrantRepo.save(ment);
                }
                //Add up scores for each tournamentEntrant for each event.
                for (TournamentEntrant ment : tournamentEntrants) {
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
                sortedEntrants = tournamentEntrantRepo.findAllByTournamentOrderByTotalScoreAsc(t);
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

package com.syftgolf.syftgolf.service;


import com.syftgolf.syftgolf.entity.*;
import com.syftgolf.syftgolf.entity.vm.TournamentVM;
import com.syftgolf.syftgolf.repository.*;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentService {

    /**
     * Get list of entrants to a tournament
     * save a tournament
     * Update a tournament
     * Complete a tournament
     */

    TournamentRepo tournamentRepo;

    TournamentEntrantService tournamentEntrantService;

    SocietyRepo societyRepo;

    CourseRepo courseRepo;

    MemberRepo memberRepo;

    EventRepo eventRepo;

    public TournamentService(TournamentEntrantService tournamentEntrantService, EventRepo eventRepo, TournamentRepo tournamentRepo, SocietyRepo societyRepo, CourseRepo courseRepo, MemberRepo memberRepo) {
        this.tournamentRepo = tournamentRepo;
        this.societyRepo = societyRepo;
        this.courseRepo = courseRepo;
        this.memberRepo = memberRepo;
        this.eventRepo = eventRepo;
        this.tournamentEntrantService = tournamentEntrantService;
    }

    /**
     *
     * @param eventName name of the event
     * @return a list of members who have entered this event
     */
    public List<Member> getEntrants(String eventName){
        Tournament event = tournamentRepo.findMultiEventByName(eventName);
        List<TournamentEntrant> entrants = event.getTournamentEntrants();
        List<Member> members = new ArrayList<>();
        for(TournamentEntrant entrant: entrants) {
            members.add(entrant.getMember());
        }
        return members;
    }

    public GenericResponse save(long societyId, Tournament tournament) {
        Society society = societyRepo.getById(societyId);
        tournament.setSociety(society);
        List<Tournament> tournaments = society.getTournaments();
        tournaments.add(tournament);
        society.setTournaments(tournaments);
        societyRepo.save(society);
        tournamentRepo.save(tournament);
        return new GenericResponse("Tournament added");
    }

    public GenericResponse addEvent(long tournamentId, long eventId) {
        Tournament t = tournamentRepo.getById(tournamentId);
        Event e = eventRepo.getById(eventId);
        List<Event> events = t.getEvents();
        events.add(e);
        t.setNoOfEvents(t.getNoOfEvents() + 1);
        tournamentRepo.save(t);
        e.setTournament(t);
        eventRepo.save(e);
        return new GenericResponse(e.getName() + " added to the tournament " + t.getName());
    }

    public GenericResponse removeEvent(long tournamentId, long eventId) {
        Tournament t = tournamentRepo.getById(tournamentId);
        Event e = eventRepo.getById(eventId);
        List<Event> events = t.getEvents();
        events.remove(e);
        t.setNoOfEvents(t.getNoOfEvents() - 1);
        tournamentRepo.save(t);
        e.setTournament(null);
        eventRepo.save(e);
        return new GenericResponse(e.getName() + " removed from the tournament " + t.getName());
    }

    /**
     *
     * @param tournamentId id of the tournament to update
     * @param tournamentVM tournament body to update
     * @return a success message
     */
    public GenericResponse update(long tournamentId, TournamentVM tournamentVM) {

        tournamentRepo.findById(tournamentId).ifPresent(tournament -> {
            tournament.setName(tournamentVM.getName());
            tournament.setStartDate(tournamentVM.getStartDate());
            tournament.setEndDate(tournamentVM.getEndDate());
            tournament.setNoOfEvents(tournamentVM.getNoOfEvents());
            tournament.setType(tournamentVM.getType());
            System.out.println(tournament.getName());
            tournamentRepo.save(tournament);
        });
        return new GenericResponse("Tournament updated");
    }

    /**
     *
     * @param tournamentId id of tournament to delete
     * @return a message of success
     */
    public GenericResponse delete(long tournamentId) {

        Tournament t = tournamentRepo.findTournamentById(tournamentId);
        //Remove tournament from the societies list of tournaments
            Society s = t.getSociety();
            List<Tournament> tournaments = s.getTournaments();
            tournaments.removeIf(ts -> ts.getId() == tournamentId);
            s.setTournaments(tournaments);
            societyRepo.save(s);
            //Unlink all events to the tournament
            List<Event> events = t.getEvents();
            for(Event ev : events) {
                ev.setTournament(null);
            }
            //Delete the tournament
            tournamentRepo.deleteById(tournamentId);
            return new GenericResponse("Tournament removed");
        }

    /**
     *
     * @param tournamentId id of tournament to complete
     * @return the completed tournament
     */
    public Tournament complete(long tournamentId) {
        Tournament t = tournamentRepo.findTournamentById(tournamentId);
        t.setStatus("Complete");
        tournamentEntrantService.getEntrants(t.getId());
        return t;
    }

    public Page<Tournament> getPageOfTournaments(long societyId, Pageable page) {
        return tournamentRepo.findAllByDate(societyId, page);
    }

    public Page<Tournament> getPageOfPreviousTournaments(long societyId, Pageable page) {
        return tournamentRepo.findAllByDateBefore(page, societyId);
    }

    public List<Tournament> listTournaments(long societyId) {
        return tournamentRepo.findAllBySocietyIdOrderByNameAsc(societyId);
    }
}

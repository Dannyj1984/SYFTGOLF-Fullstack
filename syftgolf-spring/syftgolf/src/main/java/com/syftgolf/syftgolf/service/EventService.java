package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.*;
import com.syftgolf.syftgolf.entity.vm.event.EventUpdateVM;
import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.repository.*;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    /**
     * Get list of entrants to an event
     * save an event
     * Get a page of upcoming events
     * Get a page of previous events
     * Update an event
     * Complete an event
     */

    EventRepo eventRepo;

    SocietyRepo societyRepo;

    CourseRepo courseRepo;

    MemberRepo memberRepo;

    EntrantsRepo entrantsRepo;

    TournamentRepo tournamentRepo;

    TournamentEntrantRepo tournamentEntrantRepo;

    public EventService(EventRepo eventRepo, TournamentRepo tournamentRepo, TournamentEntrantRepo tournamentEntrantRepo, EntrantsRepo entrantsRepo, SocietyRepo societyRepo, CourseRepo courseRepo, MemberRepo memberRepo) {
        this.eventRepo = eventRepo;
        this.entrantsRepo = entrantsRepo;
        this.societyRepo = societyRepo;
        this.courseRepo = courseRepo;
        this.memberRepo = memberRepo;
        this.tournamentRepo = tournamentRepo;
        this.tournamentEntrantRepo = tournamentEntrantRepo;
    }

    /**
     *
     * @param eventId id of the event
     * @return a list of members who have entered this event
     */
    public List<Member> getEntrants(long eventId){
        Event event = eventRepo.findEventById(eventId);
        List<Entrants> entrants = event.getEntrants();
        String m = event.getEntrants().toString();
        System.out.println(m);
        System.out.println(entrants.size());
        List<Member> members = new ArrayList<>();
        for(Entrants entrant: entrants) {
            members.add(entrant.getMember());
        }
        return members;
    }


    /**
     *
     * @param societyId id of the society the event belongs to
     * @param courseId id of the course the event is played at
     * @param event the event body to be saved
     * @return a message to say the event is saved
     */
    public GenericResponse save(long societyId, long courseId, Event event) {
        Course course = courseRepo.findById(courseId);
        List<Event> ev = course.getEvents();
        ev.add(event);
        event.setCourse(course);
        societyRepo.findById(societyId)
                .ifPresent(society -> {
                    List<Event> eve = society.getEvents();
                    eve.add(event);
                    event.setSociety(society);
                    eventRepo.save(event);
                });
        return new GenericResponse("Event saved");
    }

    //Get a page of upcoming events
    public Page<Event> getEvents(Pageable pageable, long id) {
        return eventRepo.findAllByDate(pageable, id);
    }

    //Get a page of previous events
    public Page<Event> getPreviousEvents(Pageable pageable, long id) {
        return eventRepo.findAllByDateBefore(pageable, id);
    }

    /**
     *
     * @param eventId id of the event to update
     * @param eventVM the body of the update
     * @return a message informing of successful update
     */
    //TODO uncomment out code to add course
    public GenericResponse update(long eventId, EventUpdateVM eventVM) {
        eventRepo.findById(eventId)
                .ifPresent(event -> {
                    event.setName(eventVM.getName());
                    event.setDate(eventVM.getDate());
                    event.setQualifier(eventVM.getQualifier());
                    event.setInfo(eventVM.getInfo());
                    event.setType(eventVM.getType());
                    event.setCost(eventVM.getCost());
                    event.setNinetyFivePercent(eventVM.getNinetyFivePercent());
                    event.setMaxEntrants(eventVM.getMaxEntrants());
                    event.setWinner(eventVM.getWinner());
//                    Course c = courseRepo.findByName(eventVM.getName());
//                    event.setCourse(c);
//                    List<Event> events = c.getEvents();
//                    events.add(event);
//                    courseRepo.save(c);
                    eventRepo.save(event);
                });
        return new GenericResponse("Event updated");
    }

    /**
     *
     * @param eventId id of the event
     * @return a message
     */
    public GenericResponse complete(long eventId) {
        Event e = eventRepo.getById(eventId);
        double winningMargin = 0.0;
        GenericResponse response = null;
        List<Member> members = new ArrayList<>();
        List<Entrants> entrants = e.getEntrants();
        Tournament t = new Tournament();
        try {
            t = e.getTournament();
        } catch (Error error) {
        }
        for(Entrants ent : entrants) {
            members.add(ent.getMember());
        }

        if(e.getType().equals("Stableford")) {
            List<Entrants> en = e.getEntrants();
            //Sort list of entrants from highest score to lowest
            List<Entrants> sortedEntrants = en.stream().sorted(Comparator.comparing(Entrants::getScore).reversed()).collect(Collectors.toList());
            memberRepo.findById(sortedEntrants.get(0).getMember().getId())
                    .ifPresent(member -> member.setWins(member.getWins() + 1));
            e.setStatus("Complete");
            e.setWinner(sortedEntrants.get(0).getMember().getFirstName());
            eventRepo.save(e);
            response = new GenericResponse("Event completed, winner is " + sortedEntrants.get(0).getMember().getFirstName() + " " + sortedEntrants.get(0).getMember().getSurname() + " with a score of " + sortedEntrants.get(0).getScore());
            winningMargin = sortedEntrants.get(0).getScore() - sortedEntrants.get(1).getScore();
            getSortedEntrantMember(e, winningMargin, sortedEntrants);
        }
        if(e.getType().equals("Medal")) {
            System.out.println("Event is medal");
            List<Entrants> en = e.getEntrants();
            //Sort list of entrants from highest score to lowest
            List<Entrants> sortedEntrants = en.stream().sorted(Comparator.comparing(Entrants::getScore)).collect(Collectors.toList());
            memberRepo.findById(sortedEntrants.get(0).getMember().getId())
                    .ifPresent(member -> member.setWins(member.getWins() + 1));
            e.setStatus("Complete");
            e.setWinner(sortedEntrants.get(0).getMember().getFirstName());
            eventRepo.save(e);
            response =  new GenericResponse("Event completed, winner is " + sortedEntrants.get(0).getMember().getFirstName() + " " + sortedEntrants.get(0).getMember().getSurname());
            winningMargin = sortedEntrants.get(1).getScore() - sortedEntrants.get(0).getScore();
            getSortedEntrantMember(e, winningMargin, sortedEntrants);
        }

        //Add 1 to the number of events played for this member if the event is between april and october
        if(withinRange(e.getDate())) {
            for (Member mem : members) {
                mem.setEventsPlayed(mem.getEventsPlayed() + 1);
            }
            updateFedExScores(e.getId());
        }

        //Save the members courseHcp at the time of the event to be used in the future to show the handicap at the time of this event
        if(e.getNinetyFivePercent()) {
            for (Entrants entrants1 : entrants) {
                double handicap = entrants1.getMember().getHandicap() - entrants1.getMember().getSocHcpRed();
                double slopeAdjusted = handicap * (e.getCourse().getSlopeRating() / 113);
                entrants1.setCoursehcp((int) Math.round(slopeAdjusted * 0.95) );
                entrantsRepo.save(entrants1);
            }
        }
        if(!e.getNinetyFivePercent()) {
            for (Entrants entrants1 : entrants) {
                double handicap = entrants1.getMember().getHandicap() - entrants1.getMember().getSocHcpRed();
                double slopeAdjusted = handicap * (e.getCourse().getSlopeRating() / 113);
                entrants1.setCoursehcp((int) Math.round(slopeAdjusted));
                entrantsRepo.save(entrants1);
            }
        }
            if (t == null) {
            } else {
                List<Tournament> tournaments = tournamentRepo.findAll();
                for (Tournament tournament : tournaments) {
                    if (t.equals(tournament)) {
                        List<TournamentEntrant> tEntrants = tournamentEntrantRepo.findAllByTournamentOrderByTotalScoreAsc(t);

                        for (TournamentEntrant te : tEntrants) {
                            for (Entrants en : entrants) {
                                if (te.getMember().getUsername().equals(en.getMember().getUsername())) {
                                    te.setEventsPlayed(te.getEventsPlayed() +1);
                                    tournamentEntrantRepo.save(te);
                                }
                            }
                        }
                    }
                }
            }


        return response;
    }

    private void getSortedEntrantMember(Event e, double winningMargin, List<Entrants> sortedEntrants) {
        Member m = sortedEntrants.get(0).getMember();
        if(e.getCurrentEntrants() >= 6) {
            if (winningMargin < 6.0) {
                m.setSocHcpRed(m.getSocHcpRed() + 1);
                memberRepo.save(m);
            } else if (winningMargin >= 6.0 && winningMargin < 9.0) {
                m.setSocHcpRed(m.getSocHcpRed() + 2);
                memberRepo.save(m);
            } else if (winningMargin >= 9) {
                m.setSocHcpRed(m.getSocHcpRed() + 3);
                memberRepo.save(m);
            }
        }
    }


    public Event getByEventName(String eventName) {
        Event inDB = eventRepo.findEventByName(eventName);
        if(inDB == null) {
            throw new NotFoundException(eventName + " not found");
        }
        return inDB;
    }

    public List<Entrants> updateFedExScores(long eventId) {
        Event e = eventRepo.getEventById(eventId);
        List<Entrants> entrants = e.getEntrants();
        int fedExPoints = entrants.size();
            entrants.sort(Entrants.entrantScoreMedal);
            for(Entrants ent: entrants) {
                Member m = ent.getMember();
                m.setFedExPoints(m.getFedExPoints() + fedExPoints);
                memberRepo.save(m);
                fedExPoints--;
            }
        return entrants;
    }

    public List<Event> getEventsList(long societyId) {
        return eventRepo.findAllByDate(societyId);
    }

    public List<Event> getPreviousEventsList(long societyId) {
        return eventRepo.findAllByDateBefore(societyId);
    }

    public boolean withinRange(LocalDate date) {
        LocalDate start = LocalDate.of(2022, Month.APRIL, 1);
        LocalDate end = LocalDate.of(2022, Month.OCTOBER, 31);
        return date.isAfter(start) && date.isBefore(end);
    }
}

package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.*;
import com.syftgolf.syftgolf.error.NotFoundException;
import com.syftgolf.syftgolf.repository.*;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class EntrantsService {

    /**
     * Save new entrant to an event
     * Update an entrants score
     * delete an entrant
     */

    EntrantsRepo entrantsRepo;

    EventRepo eventRepo;

    MemberRepo memberRepo;

    ScoreCardRepo scoreCardRepo;

    TeeSheetRepo teeSheetRepo;

    public EntrantsService(TeeSheetRepo teeSheetRepo, EntrantsRepo entrantsRepo, EventRepo eventRepo, MemberRepo memberRepo, ScoreCardRepo scoreCardRepo) {
        this.entrantsRepo = entrantsRepo;
        this.eventRepo = eventRepo;
        this.memberRepo = memberRepo;
        this.scoreCardRepo = scoreCardRepo;
        this.teeSheetRepo = teeSheetRepo;
    }

    /**
     *  @param eventId id of the event
     * @param memberId id of the member to enter
     */
    public GenericResponse save(long eventId, long memberId) {
        GenericResponse gr = new GenericResponse("This member is already entered in this event");
        Event e = eventRepo.findEventById(eventId);
        Member m = memberRepo.findMemberById(memberId);
        Entrants en = new Entrants(m, e, 0, 0);
        //Update the current entrants entered in an event by 1
        e.setCurrentEntrants(e.getCurrentEntrants() + 1);
        eventRepo.save(e);
        boolean entered = false;
        //Get the current list of entrants for this event
        List<Entrants> entrants = e.getEntrants();

        //Check if member is already entered in this event
        for(Entrants entrants1 : entrants) {
            System.out.println(entrants1.getMember().getUsername());
            System.out.println(m.getUsername());
            if (entrants1.getMember().getUsername().equals(m.getUsername())) {
                entered = true;
                break;
            }
        }
            if(!entered) {
                //Add the current entrant to the list of entrants for this event.
                entrants.add(en);
                entrantsRepo.save(en);
                ScoreCard sc = new ScoreCard();
                //Ensure holes are sorted by hole number
                List<Hole> holes = e.getCourse().getHoles();
                System.out.println(holes);
                holes.sort(Comparator.comparingInt(Hole::getHoleNumber));
                memberRepo.save(m);
                try {
                    sc.setH1Index(holes.get(0).getStrokeIndex());
                    sc.setH1Par(holes.get(0).getPar());
                    sc.setH2Index(holes.get(1).getStrokeIndex());
                    sc.setH2Par(holes.get(1).getPar());
                    sc.setH3Index(holes.get(2).getStrokeIndex());
                    sc.setH3Par(holes.get(2).getPar());
                    sc.setH4Index(holes.get(3).getStrokeIndex());
                    sc.setH4Par(holes.get(3).getPar());
                    sc.setH5Index(holes.get(4).getStrokeIndex());
                    sc.setH5Par(holes.get(4).getPar());
                    sc.setH6Index(holes.get(5).getStrokeIndex());
                    sc.setH6Par(holes.get(5).getPar());
                    sc.setH7Index(holes.get(6).getStrokeIndex());
                    sc.setH7Par(holes.get(6).getPar());
                    sc.setH8Index(holes.get(7).getStrokeIndex());
                    sc.setH8Par(holes.get(7).getPar());
                    sc.setH9Index(holes.get(8).getStrokeIndex());
                    sc.setH9Par(holes.get(8).getPar());
                    sc.setH10Index(holes.get(9).getStrokeIndex());
                    sc.setH10Par(holes.get(9).getPar());
                    sc.setH11Index(holes.get(10).getStrokeIndex());
                    sc.setH11Par(holes.get(10).getPar());
                    sc.setH12Index(holes.get(11).getStrokeIndex());
                    sc.setH12Par(holes.get(11).getPar());
                    sc.setH13Index(holes.get(12).getStrokeIndex());
                    sc.setH13Par(holes.get(12).getPar());
                    sc.setH14Index(holes.get(13).getStrokeIndex());
                    sc.setH14Par(holes.get(13).getPar());
                    sc.setH15Index(holes.get(14).getStrokeIndex());
                    sc.setH15Par(holes.get(14).getPar());
                    sc.setH16Index(holes.get(15).getStrokeIndex());
                    sc.setH16Par(holes.get(15).getPar());
                    sc.setH17Index(holes.get(16).getStrokeIndex());
                    sc.setH17Par(holes.get(16).getPar());
                    sc.setH18Index(holes.get(17).getStrokeIndex());
                    sc.setH18Par(holes.get(17).getPar());
                } catch (Error error) {
                    GenericResponse errorResponse = new GenericResponse("Entrant not saved. Please ensure all holes exist for this course");
                    gr = errorResponse;
                }
                sc.setEntrants(en);
                en.setScoreCard(sc);
                scoreCardRepo.save(sc);
                entrantsRepo.save(en);
                gr = new GenericResponse("Entered");
            }

        return gr;
    }

    /**
     *
     * @param eventId the event name
     * @param memberId the id of the member to update the score of
     */

    public void updateScore(long eventId, long memberId, double score) {
        Event e = eventRepo.findEventById(eventId);
        Member m = memberRepo.findMemberById(memberId);
        Entrants en = entrantsRepo.getEntrantsByMemberAndEvent(m, e);
        System.out.println(en.getScore());
        en.setScore(score);
        System.out.println(en.getScore());
        entrantsRepo.save(en);
    }

    /**
     *
     * @param memberId id of member to remove
     * @param eventId id of event to remove from
     * @return a message of successful removal
     */
    public GenericResponse deleteEntrant(long memberId, long eventId) {
        GenericResponse gr = new GenericResponse("This member is not in this event");
        Event e = eventRepo.getById(eventId);
        Member m = memberRepo.getById(memberId);
        ScoreCard sc = new ScoreCard();
        List<Entrants> entrants = e.getEntrants();
        boolean entered = false;
        int index = 0;
        //Check if member is already entered in this event
        for(Entrants entrants1 : entrants) {
            if (entrants1.getMember().getUsername().equals(m.getUsername())) {
                sc = entrants1.getScoreCard();
                entered = true;
                gr = new GenericResponse("Member not entered");
                break;
            }
        }
        if(entered) {
            entrantsRepo.deleteByMemberAndEvent(m, e);
            scoreCardRepo.delete(sc);
            e.setCurrentEntrants(e.getCurrentEntrants() - 1);
            for(Entrants entrants1 : entrants) {
                if (entrants1.getMember().getUsername().equals(m.getUsername())) {
                    index = entrants.indexOf(entrants1);
                }
            }
            entrants.remove(index);
            e.setEntrants(entrants);
            eventRepo.save(e);

            gr = new GenericResponse("Member Removed");
        }
        return gr;
    }


    /**
     *
     * @param eventId event the entrants belongs to
     * @return a new list of teesheets which are updated with the members
     */
    public List<TeeSheet> randomiseEntrants(long eventId, double perTee) {
        //Get info
        Event e = eventRepo.findEventById(eventId); // get event
        List<TeeSheet> teeTimes = e.getTeeSheets(); //Get a list of tee sheets for this event
        List<Entrants> entrants = e.getEntrants(); //Get a list of entrants for this event
        double noOfEntrants = entrants.size(); //Number of entrants.
        double noOfTeeSheets = teeTimes.size(); //number of teeSheets already created.
        float teeSheetsNeeded = (float) Math.ceil(noOfEntrants/perTee); //How many tee sheets required.

        //If not enough teetimes created for the number of entrants, show an error
        if( teeSheetsNeeded > noOfTeeSheets) {
            throw new NotFoundException("Not enough tee times for the number of entrants");
        } else {
            Collections.shuffle(entrants); //Shuffle list of entrants
            List<Entrants> randomMemberList = new ArrayList<>(entrants); //create new List of entrants from the shuffled list

            for (int i = 0; i < teeSheetsNeeded; i++) { //For the number of teesheets needed
                List<Entrants> addEntrants = new ArrayList<>(); //New List of entrants to add to the tee sheet
                for (int j = 0; j < perTee; j++) { //For the number of players per tee
                    if(randomMemberList.isEmpty()) {
                        break;
                    } else {
                        addEntrants.add(randomMemberList.get(0)); //add first entrant to the addEntrants list
                        randomMemberList.remove(0); //Remove the first entrant and repeat for the number of times there are slots to fill
                    }
                }
                teeTimes.get(i).setEntrants(addEntrants); // set the tee sheets entrants to the addEntrants list of entrants
                //newTee.add(teeTimes.get(i));
                teeSheetRepo.save(teeTimes.get(i));

            }
            e.setTeeSheets(teeTimes);
            eventRepo.save(e);
        }
        return teeTimes;
    }
}

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
        GenericResponse gr = new GenericResponse();
        Event e = eventRepo.findEventById(eventId);
        Member m = memberRepo.getOne(memberId);
        Entrants en = new Entrants(m, e, 0);
        //Update the current entrants entered in an event by 1
        e.setCurrentEntrants(e.getCurrentEntrants() + 1);
        eventRepo.save(e);
        //Get the current list of entrants for this event
        List<Entrants> entrants = e.getEntrants();
        //Add the current entrant to the list of entrants for this event.
        entrants.add(en);
        entrantsRepo.save(en);
        ScoreCard sc = new ScoreCard();
        //Ensure holes are sorted by hole number
        List<Hole> holes = e.getCourse().getHoles();
        System.out.println(holes);
        holes.sort(Comparator.comparingInt(Hole::getHoleNumber));
        try {
            sc.setH1Index(holes.get(0).getStrokeIndex());
            sc.setH2Index(holes.get(1).getStrokeIndex());
            sc.setH3Index(holes.get(2).getStrokeIndex());
            sc.setH4Index(holes.get(3).getStrokeIndex());
            sc.setH5Index(holes.get(4).getStrokeIndex());
            sc.setH6Index(holes.get(5).getStrokeIndex());
            sc.setH7Index(holes.get(6).getStrokeIndex());
            sc.setH8Index(holes.get(7).getStrokeIndex());
            sc.setH9Index(holes.get(8).getStrokeIndex());
            sc.setH10Index(holes.get(9).getStrokeIndex());
            sc.setH11Index(holes.get(10).getStrokeIndex());
            sc.setH12Index(holes.get(11).getStrokeIndex());
            sc.setH13Index(holes.get(12).getStrokeIndex());
            sc.setH14Index(holes.get(13).getStrokeIndex());
            sc.setH15Index(holes.get(14).getStrokeIndex());
            sc.setH16Index(holes.get(15).getStrokeIndex());
            sc.setH17Index(holes.get(16).getStrokeIndex());
            sc.setH18Index(holes.get(17).getStrokeIndex());
        } catch (Error error) {
            GenericResponse errorResponse = new GenericResponse("Entrant not saved. Please ensure all holes exist for this course");
            gr = errorResponse;
        }
            sc.setEntrants(en);
            en.setScoreCard(sc);
            scoreCardRepo.save(sc);
            entrantsRepo.save(en);
            GenericResponse completeResponse = new GenericResponse("Entered");
            gr = completeResponse;

        return gr;
    }

    /**
     *
     * @param eventId the event name
     * @param memberId the id of the member to update the score of
     */

    public void updateScore(long eventId, long memberId) {
        Event e = eventRepo.findEventById(eventId);
        List<Entrants> entrants = e.getEntrants();

        for (Entrants en : entrants) {
            if(e.getType().equals("Medal")) {
                if (en.getMember().getId() == memberId) {
                    en.setScore(en.getScoreCard().getTotalNettScore());
                    entrantsRepo.save(en);
                }
            } else if(e.getType().equals("Stableford")){
                if (en.getMember().getId() == memberId) {
                    en.setScore(en.getScoreCard().getTotalStablefordScore());
                    entrantsRepo.save(en);
                }
            }
        }
    }

    /**
     *
     * @param memberId id of member to remove
     * @param eventId id of event to remove from
     * @return a message of successful removal
     */
    public GenericResponse deleteEntrant(long memberId, long eventId) {
        Event e = eventRepo.getById(eventId);
        Member m = memberRepo.getById(memberId);
        entrantsRepo.deleteByMemberAndEvent(m, e);
        return new GenericResponse("Entrant removed");
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

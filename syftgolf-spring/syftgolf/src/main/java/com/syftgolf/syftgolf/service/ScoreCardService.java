package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.Entrants;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.ScoreCard;
import com.syftgolf.syftgolf.entity.vm.ScoreCardVM;
import com.syftgolf.syftgolf.repository.EntrantsRepo;
import com.syftgolf.syftgolf.repository.EventRepo;
import com.syftgolf.syftgolf.repository.MemberRepo;
import com.syftgolf.syftgolf.repository.ScoreCardRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreCardService {

    /**
     * Get scorecard
     * Update scoreCard
     */

    ScoreCardRepo scoreCardRepo;

    MemberRepo memberRepo;

    EventRepo eventRepo;

    EntrantsRepo entrantsRepo;

    public ScoreCardService(EntrantsRepo entrantsRepo, ScoreCardRepo scoreCardRepo, MemberRepo memberRepo, EventRepo eventRepo) {
        this.scoreCardRepo = scoreCardRepo;
        this.memberRepo = memberRepo;
        this.eventRepo = eventRepo;
        this.entrantsRepo = entrantsRepo;
    }

    public ScoreCard getScoreCard(long memberId, long eventId) {
        Member m = memberRepo.getById(memberId);
        Event e = eventRepo.getById(eventId);
        Entrants en = entrantsRepo.getEntrantsByMemberAndEvent(m, e);
        long scoreCardId = en.getScoreCard().getId();
        return scoreCardRepo.getById(scoreCardId);
    }

    /**
     *
     * @param eventId if of the event
     * @param memberId id of the member
     * @param scorecard scorecard object to update with
     */
    //TODO make this more efficient
    public void update(long eventId, long memberId, int currentHole, ScoreCardVM scorecard) {
        Event e = eventRepo.findEventById(eventId);
        Member m = memberRepo.findMemberById(memberId);
        double courseHandicapDouble = m.getHandicap()/113*e.getCourse().getSlopeRating(); //4.1 / 113 * 130 = 4.7168
        int courseHcp = 0;
        courseHcp = (int) Math.round(courseHandicapDouble); //5
        if(e.getNinetyFivePercent()) {
            courseHandicapDouble = (courseHcp * 0.95); //4.75
        }

        courseHcp = (int) Math.round(courseHandicapDouble) - m.getSocHcpRed();
        int stableFordTotal = 0;
        int medalTotal = 0;
        int nettTotal = 0;
        List<Entrants> en = entrantsRepo.findAllByEvent(e);
        for(Entrants ent : en) {
            if (ent.getMember().equals(m)) {
                //Hole 1

                if(scorecard.getH1Score() > 0) {
                    ent.getScoreCard().setH1Score(scorecard.getH1Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(0).getStrokeIndex())) {
                        ent.getScoreCard().setH1NettScore(scorecard.getH1Score() - 1);
                        System.out.println("Get a shot on this hole");
                        System.out.println("Hole si = " + e.getCourse().getHoles().get(0).getStrokeIndex());
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(0).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH1NettScore(scorecard.getH1Score() -2);
                        System.out.println("Gets 2 shots on this hole");
                        System.out.println("Hole si = " + e.getCourse().getHoles().get(0).getStrokeIndex());
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(0).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH1NettScore(scorecard.getH1Score() -1);
                        System.out.println("Gets 1 shot on this hole");
                        System.out.println("Hole si = " + e.getCourse().getHoles().get(0).getStrokeIndex());
                    } else {
                        ent.getScoreCard().setH1NettScore(scorecard.getH1Score());
                        System.out.println("No shot on this hole");
                        System.out.println("Hole si = " + e.getCourse().getHoles().get(0).getStrokeIndex());
                    }
                    ent.getScoreCard().setH1Points(setPoints(ent.getScoreCard().getH1NettScore(), e.getCourse().getHoles().get(0).getPar()));
                    //Update running total scores
                    medalTotal += ent.getScoreCard().getH1Score();
                    stableFordTotal += ent.getScoreCard().getH1Points();
                    nettTotal += ent.getScoreCard().getH1NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());
                }

                //Hole 2

                if(scorecard.getH2Score() > 0) {
                    ent.getScoreCard().setH2Score(scorecard.getH2Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(1).getStrokeIndex())) {
                        ent.getScoreCard().setH2NettScore(scorecard.getH2Score() - 1);
                        System.out.println("Get a shot on this hole");
                        System.out.println("Hole si = " + e.getCourse().getHoles().get(1).getStrokeIndex());
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(1).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH2NettScore(scorecard.getH2Score() -2);
                        System.out.println("Gets 2 shots on this hole");
                        System.out.println("Hole si = " + e.getCourse().getHoles().get(1).getStrokeIndex());
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(1).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH2NettScore(scorecard.getH2Score() -1);
                        System.out.println("Gets 1 shot on this hole");
                        System.out.println("Hole si = " + e.getCourse().getHoles().get(1).getStrokeIndex());
                    } else {
                        ent.getScoreCard().setH2NettScore(scorecard.getH2Score());
                        System.out.println("No shot on this hole");
                        System.out.println("Hole si = " + e.getCourse().getHoles().get(1).getStrokeIndex());
                    }
                    ent.getScoreCard().setH2Points(setPoints(ent.getScoreCard().getH2NettScore(), e.getCourse().getHoles().get(1).getPar()));
                    medalTotal += ent.getScoreCard().getH2Score();
                    stableFordTotal += ent.getScoreCard().getH2Points();
                    nettTotal += ent.getScoreCard().getH2NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());
                }


                    //Hole 3
                    if(scorecard.getH3Score() > 0) {
                    ent.getScoreCard().setH3Score(scorecard.getH3Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(2).getStrokeIndex())) {
                        ent.getScoreCard().setH3NettScore(scorecard.getH3Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(2).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH3NettScore(scorecard.getH3Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(2).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH3NettScore(scorecard.getH3Score() -1);
                    } else {
                        ent.getScoreCard().setH3NettScore(scorecard.getH3Score());
                    }
                    ent.getScoreCard().setH3Points(setPoints(ent.getScoreCard().getH3NettScore(), e.getCourse().getHoles().get(2).getPar()));

                    medalTotal += ent.getScoreCard().getH3Score();
                    stableFordTotal += ent.getScoreCard().getH3Points();
                    nettTotal += ent.getScoreCard().getH3NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 4

                if(scorecard.getH4Score() > 0) {
                    ent.getScoreCard().setH4Score(scorecard.getH4Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(3).getStrokeIndex())) {
                        ent.getScoreCard().setH4NettScore(scorecard.getH4Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(3).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH3NettScore(scorecard.getH3Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(3).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH4NettScore(scorecard.getH4Score() -1);
                    } else {
                        ent.getScoreCard().setH4NettScore(scorecard.getH4Score());
                    }
                    ent.getScoreCard().setH4Points(setPoints(ent.getScoreCard().getH4NettScore(), e.getCourse().getHoles().get(3).getPar()));
                    medalTotal += ent.getScoreCard().getH4Score();
                    stableFordTotal += ent.getScoreCard().getH4Points();
                    nettTotal += ent.getScoreCard().getH4NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 5

                if(scorecard.getH5Score() > 0) {
                    ent.getScoreCard().setH5Score(scorecard.getH5Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(4).getStrokeIndex())) {
                        ent.getScoreCard().setH5NettScore(scorecard.getH5Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(4).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH5NettScore(scorecard.getH5Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(4).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH5NettScore(scorecard.getH5Score() -1);
                    } else {
                        ent.getScoreCard().setH5NettScore(scorecard.getH5Score());
                    }
                    ent.getScoreCard().setH5Points(setPoints(ent.getScoreCard().getH5NettScore(), e.getCourse().getHoles().get(4).getPar()));
                    medalTotal += ent.getScoreCard().getH5Score();
                    stableFordTotal += ent.getScoreCard().getH5Points();
                    nettTotal += ent.getScoreCard().getH5NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 6

                if(scorecard.getH6Score() > 0) {
                    ent.getScoreCard().setH6Score(scorecard.getH6Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(5).getStrokeIndex())) {
                        ent.getScoreCard().setH6NettScore(scorecard.getH6Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(5).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH6NettScore(scorecard.getH6Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(5).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH6NettScore(scorecard.getH6Score() -1);
                    } else {
                        ent.getScoreCard().setH6NettScore(scorecard.getH6Score());
                    }
                    ent.getScoreCard().setH6Points(setPoints(ent.getScoreCard().getH6NettScore(), e.getCourse().getHoles().get(5).getPar()));
                    ent.getScoreCard().setH6Points(setPoints(ent.getScoreCard().getH6NettScore(), e.getCourse().getHoles().get(5).getPar()));
                    medalTotal += ent.getScoreCard().getH6Score();
                    stableFordTotal += ent.getScoreCard().getH6Points();
                    nettTotal += ent.getScoreCard().getH6NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 7

                if(scorecard.getH7Score() > 0) {
                    ent.getScoreCard().setH7Score(scorecard.getH7Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(6).getStrokeIndex())) {
                        ent.getScoreCard().setH7NettScore(scorecard.getH7Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(6).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH7NettScore(scorecard.getH7Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(6).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH7NettScore(scorecard.getH7Score() -1);
                    } else {
                        ent.getScoreCard().setH7NettScore(scorecard.getH7Score());
                    }
                    ent.getScoreCard().setH7Points(setPoints(ent.getScoreCard().getH7NettScore(), e.getCourse().getHoles().get(6).getPar()));
                    medalTotal += ent.getScoreCard().getH7Score();
                    stableFordTotal += ent.getScoreCard().getH7Points();
                    nettTotal += ent.getScoreCard().getH7NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 8

                if(scorecard.getH8Score() > 0) {
                    ent.getScoreCard().setH8Score(scorecard.getH8Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(7).getStrokeIndex())) {
                        ent.getScoreCard().setH8NettScore(scorecard.getH8Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(7).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH8NettScore(scorecard.getH8Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(7).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH8NettScore(scorecard.getH8Score() -1);
                    } else {
                        ent.getScoreCard().setH8NettScore(scorecard.getH8Score());
                    }
                    ent.getScoreCard().setH8Points(setPoints(ent.getScoreCard().getH8NettScore(), e.getCourse().getHoles().get(7).getPar()));
                    medalTotal += ent.getScoreCard().getH8Score();
                    stableFordTotal += ent.getScoreCard().getH8Points();
                    nettTotal += ent.getScoreCard().getH8NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 9

                if(scorecard.getH9Score() > 0) {
                    ent.getScoreCard().setH9Score(scorecard.getH9Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(8).getStrokeIndex())) {
                        ent.getScoreCard().setH9NettScore(scorecard.getH9Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(8).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH9NettScore(scorecard.getH9Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(8).getStrokeIndex() + 19))) {
                        ent.getScoreCard().setH9NettScore(scorecard.getH9Score() -1);
                    } else {
                        ent.getScoreCard().setH9NettScore(scorecard.getH9Score());
                    }
                    ent.getScoreCard().setH9Points(setPoints(ent.getScoreCard().getH9NettScore(), e.getCourse().getHoles().get(8).getPar()));
                    medalTotal += ent.getScoreCard().getH9Score();
                    stableFordTotal += ent.getScoreCard().getH9Points();
                    nettTotal += ent.getScoreCard().getH9NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 10

                if(scorecard.getH10Score() > 0) {
                    ent.getScoreCard().setH10Score(scorecard.getH10Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(9).getStrokeIndex())) {
                        ent.getScoreCard().setH10NettScore(scorecard.getH10Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(9).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH10NettScore(scorecard.getH10Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(9).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH10NettScore(scorecard.getH10Score() -1);
                    } else {
                        ent.getScoreCard().setH10NettScore(scorecard.getH10Score());
                    }
                    ent.getScoreCard().setH10Points(setPoints(ent.getScoreCard().getH10NettScore(), e.getCourse().getHoles().get(9).getPar()));
                    medalTotal += ent.getScoreCard().getH10Score();
                    stableFordTotal += ent.getScoreCard().getH10Points();
                    nettTotal += ent.getScoreCard().getH10NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 11

                if(scorecard.getH11Score() > 0) {
                    ent.getScoreCard().setH11Score(scorecard.getH11Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(10).getStrokeIndex())) {
                        ent.getScoreCard().setH11NettScore(scorecard.getH11Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(10).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH11NettScore(scorecard.getH11Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(10).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH11NettScore(scorecard.getH11Score() -1);
                    } else {
                        ent.getScoreCard().setH11NettScore(scorecard.getH11Score());
                    }
                    ent.getScoreCard().setH11Points(setPoints(ent.getScoreCard().getH11NettScore(), e.getCourse().getHoles().get(10).getPar()));
                    medalTotal += ent.getScoreCard().getH11Score();
                    stableFordTotal += ent.getScoreCard().getH11Points();
                    nettTotal += ent.getScoreCard().getH11NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 12

                if(scorecard.getH12Score() > 0) {
                    ent.getScoreCard().setH12Score(scorecard.getH12Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(11).getStrokeIndex())) {
                        ent.getScoreCard().setH12NettScore(scorecard.getH12Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(11).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH12NettScore(scorecard.getH12Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(11).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH12NettScore(scorecard.getH12Score() -1);
                    } else {
                        ent.getScoreCard().setH12NettScore(scorecard.getH12Score());
                    }
                    ent.getScoreCard().setH12Points(setPoints(ent.getScoreCard().getH12NettScore(), e.getCourse().getHoles().get(11).getPar()));
                    medalTotal += ent.getScoreCard().getH12Score();
                    stableFordTotal += ent.getScoreCard().getH12Points();
                    nettTotal += ent.getScoreCard().getH12NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 13

                if(scorecard.getH13Score() > 0) {
                    ent.getScoreCard().setH13Score(scorecard.getH13Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(12).getStrokeIndex())) {
                        ent.getScoreCard().setH13NettScore(scorecard.getH13Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(12).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH13NettScore(scorecard.getH13Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(12).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH13NettScore(scorecard.getH13Score() -1);
                    } else {
                        ent.getScoreCard().setH13NettScore(scorecard.getH13Score());
                    }
                    ent.getScoreCard().setH13Points(setPoints(ent.getScoreCard().getH13NettScore(), e.getCourse().getHoles().get(12).getPar()));
                    medalTotal += ent.getScoreCard().getH13Score();
                    stableFordTotal += ent.getScoreCard().getH13Points();
                    nettTotal += ent.getScoreCard().getH13NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 14

                if(scorecard.getH14Score() > 0) {
                    ent.getScoreCard().setH14Score(scorecard.getH14Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(13).getStrokeIndex())) {
                        ent.getScoreCard().setH14NettScore(scorecard.getH14Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(13).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH14NettScore(scorecard.getH14Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(13).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH14NettScore(scorecard.getH14Score() -1);
                    } else {
                        ent.getScoreCard().setH14NettScore(scorecard.getH14Score());
                    }
                    ent.getScoreCard().setH14Points(setPoints(ent.getScoreCard().getH14NettScore(), e.getCourse().getHoles().get(13).getPar()));
                    medalTotal += ent.getScoreCard().getH14Score();
                    stableFordTotal += ent.getScoreCard().getH14Points();
                    nettTotal += ent.getScoreCard().getH14NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 15
                if(scorecard.getH15Score() > 0) {
                    ent.getScoreCard().setH15Score(scorecard.getH15Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(14).getStrokeIndex())) {
                        ent.getScoreCard().setH15NettScore(scorecard.getH15Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(14).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH15NettScore(scorecard.getH15Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(14).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH15NettScore(scorecard.getH15Score() -1);
                    } else {
                        ent.getScoreCard().setH15NettScore(scorecard.getH15Score());
                    }
                    ent.getScoreCard().setH15Points(setPoints(ent.getScoreCard().getH15NettScore(), e.getCourse().getHoles().get(14).getPar()));
                    medalTotal += ent.getScoreCard().getH15Score();
                    stableFordTotal += ent.getScoreCard().getH15Points();
                    nettTotal += ent.getScoreCard().getH15NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 16

                if(scorecard.getH16Score() > 0) {
                    ent.getScoreCard().setH16Score(scorecard.getH16Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(15).getStrokeIndex())) {
                        ent.getScoreCard().setH16NettScore(scorecard.getH16Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(15).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH16NettScore(scorecard.getH16Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(15).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH16NettScore(scorecard.getH16Score() -1);
                    } else {
                        ent.getScoreCard().setH16NettScore(scorecard.getH16Score());
                    }
                    ent.getScoreCard().setH16Points(setPoints(ent.getScoreCard().getH16NettScore(), e.getCourse().getHoles().get(15).getPar()));
                    ent.getScoreCard().setH16Points(setPoints(ent.getScoreCard().getH16NettScore(), e.getCourse().getHoles().get(15).getPar()));
                    medalTotal += ent.getScoreCard().getH16Score();
                    stableFordTotal += ent.getScoreCard().getH16Points();
                    nettTotal += ent.getScoreCard().getH16NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 17

                if(scorecard.getH17Score() > 0) {
                    ent.getScoreCard().setH17Score(scorecard.getH17Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(16).getStrokeIndex())) {
                        ent.getScoreCard().setH17NettScore(scorecard.getH17Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(16).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH17NettScore(scorecard.getH17Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(16).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH17NettScore(scorecard.getH17Score() -1);
                    } else {
                        ent.getScoreCard().setH17NettScore(scorecard.getH17Score());
                    }
                    ent.getScoreCard().setH17Points(setPoints(ent.getScoreCard().getH17NettScore(), e.getCourse().getHoles().get(16).getPar()));
                    ent.getScoreCard().setH17Points(setPoints(ent.getScoreCard().getH17NettScore(), e.getCourse().getHoles().get(16).getPar()));
                    medalTotal += ent.getScoreCard().getH17Score();
                    stableFordTotal += ent.getScoreCard().getH17Points();
                    nettTotal += ent.getScoreCard().getH17NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }

                //Hole 18

                if(scorecard.getH18Score() > 0) {
                    ent.getScoreCard().setH18Score(scorecard.getH18Score());
                    ent.setCurrentHole(currentHole);
                    //If the players course handicap is more than or equal to the stroke index for the hole, set nett score to score -1
                    if ((courseHcp <= 18) && (courseHcp >= e.getCourse().getHoles().get(17).getStrokeIndex())) {
                        ent.getScoreCard().setH18NettScore(scorecard.getH18Score() - 1);
                        //Set points for the first hole as per the setPoints helper method.
                    } else if((courseHcp > 18) && (courseHcp >= (e.getCourse().getHoles().get(17).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH18NettScore(scorecard.getH18Score() -2);
                    } else if((courseHcp > 18) && (courseHcp < (e.getCourse().getHoles().get(17).getStrokeIndex() + 18))) {
                        ent.getScoreCard().setH18NettScore(scorecard.getH18Score() -1);
                    } else {
                        ent.getScoreCard().setH18NettScore(scorecard.getH18Score());
                    }
                    ent.getScoreCard().setH18Points(setPoints(ent.getScoreCard().getH18NettScore(), e.getCourse().getHoles().get(17).getPar()));
                    ent.getScoreCard().setH18Points(setPoints(ent.getScoreCard().getH18NettScore(), e.getCourse().getHoles().get(17).getPar()));
                    medalTotal += ent.getScoreCard().getH18Score();
                    stableFordTotal += ent.getScoreCard().getH18Points();
                    nettTotal += ent.getScoreCard().getH18NettScore();
                    ent.getScoreCard().setTotalStablefordScore(stableFordTotal);
                    ent.getScoreCard().setTotalNettScore(nettTotal);
                    ent.getScoreCard().setTotalMedalScore(medalTotal);
                    if (e.getType().equals("Stableford")) {
                        ent.setScore(stableFordTotal);
                    }
                    if (e.getType().equals("Medal")) {
                        ent.setScore(nettTotal);
                    }
                    entrantsRepo.save(ent);
                    scoreCardRepo.save(ent.getScoreCard());

                }
            }
        }

    }

    /**
     * Helper method for update method
     * @param score the nett score of the player
     * @param par the par for this hole
     * @return the points scored
     */
    public int setPoints(int score, int par){
        int points =  par + 2 - score;
        int result = 0;
        if(points > 0) {
            result =  points;
        }
        return result;

    }

}

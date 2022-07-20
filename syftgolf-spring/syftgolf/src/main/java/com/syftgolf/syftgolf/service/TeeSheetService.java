package com.syftgolf.syftgolf.service;


import com.syftgolf.syftgolf.entity.Entrants;
import com.syftgolf.syftgolf.entity.Event;
import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.entity.TeeSheet;
import com.syftgolf.syftgolf.repository.EntrantsRepo;
import com.syftgolf.syftgolf.repository.EventRepo;
import com.syftgolf.syftgolf.repository.MemberRepo;
import com.syftgolf.syftgolf.repository.TeeSheetRepo;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeeSheetService {

    TeeSheetRepo teeSheetRepo;

    EventRepo eventRepo;

    MemberRepo memberRepo;

    EntrantsRepo entrantsRepo;

    public TeeSheetService(EntrantsRepo entrantsRepo, MemberRepo memberRepo, TeeSheetRepo teeSheetRepo, EventRepo eventRepo) {
        this.teeSheetRepo = teeSheetRepo;
        this.eventRepo = eventRepo;
        this.memberRepo = memberRepo;
        this.entrantsRepo = entrantsRepo;
    }

    public GenericResponse save(long eventId, TeeSheet teeSheet) {
            Event e = eventRepo.findEventById(eventId);
            TeeSheet t = new TeeSheet();
            t.setTeeTime(teeSheet.getTeeTime());
            t.setNoOfSlots(teeSheet.getNoOfSlots());
            t.setEvent(e);
            System.out.println(t);
            teeSheetRepo.save(t);
            List<TeeSheet> tees = e.getTeeSheets();
            tees.add(t);
            e.setTeeSheets(tees);
            eventRepo.save(e);
            return new GenericResponse("tee time added for " + e.getName());
    }

    public GenericResponse addEntrant(long teeSheetId, long eventId, long memberId) {
        String response = "Tee time updated";
        TeeSheet tee = teeSheetRepo.getById(teeSheetId);
        Member m = memberRepo.findMemberById(memberId);
        Event e = eventRepo.findEventById(eventId);
        Entrants ent = entrantsRepo.getEntrantsByMemberAndEvent(m, e);
        List<Entrants> entrants = tee.getEntrants();
        if(entrants.size() <= tee.getNoOfSlots()) {
            entrants.add(ent);
            tee.setEntrants(entrants);
            teeSheetRepo.save(tee);
        } else {
            response = "This tee slot is already full.";
        }
        return new GenericResponse(response);
    }

    public GenericResponse update(long teeSheetId, long memberId, long prevMemberId) throws Exception{
        GenericResponse response = new GenericResponse("Tee sheet updated");
        try {
            teeSheetRepo.update(teeSheetId, memberId, prevMemberId);
        } catch (Exception e) {
            response = new GenericResponse("This entrant is not current signed up to this event. Please register them first.");

        }
        return response;
    }
}

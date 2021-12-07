package com.syftgolf.syftgolf.event.newteesheet;

import com.syftgolf.syftgolf.event.Event;
import com.syftgolf.syftgolf.event.EventRepository;
import com.syftgolf.syftgolf.event.teesheet.TeeSheet;
import com.syftgolf.syftgolf.event.vm.NewTeeSheetUpdateVM;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewTeeSheetService {

    NewTeeSheetRepository newTeeSheetRepository;

    EventRepository eventRepository;

    public NewTeeSheetService(NewTeeSheetRepository newteeSheetRepository, EventRepository eventRepository) {
        this.newTeeSheetRepository = newteeSheetRepository;
        this.eventRepository = eventRepository;
    }

    public NewTeeSheet saveTeeSheet(NewTeeSheet newTeeSheet, long eventid) {
        System.out.println(newTeeSheet);
        Event e = eventRepository.getOne(eventid);
        NewTeeSheet newTee = new NewTeeSheet();
        newTee.setEvent(e);
        newTee.setTeetime(newTeeSheet.getTeetime());
        newTee.setPlayer1(newTeeSheet.getPlayer1());
        newTee.setPlayer2(newTeeSheet.getPlayer2());
        newTee.setPlayer3(newTeeSheet.getPlayer3());
        newTee.setPlayer4(newTeeSheet.getPlayer4());
        return newTeeSheetRepository.save(newTee);
    }

    public List<NewTeeSheet> findAllForEvent(long eventid) {
        return newTeeSheetRepository.findAllByEvent_Eventid(eventid);
    }

    //Update teesheet with any updates passed from the frontend
    public GenericResponse updateTeeSheet(long id, NewTeeSheet newTeeSheetUpdate) {
        NewTeeSheet inDB = newTeeSheetRepository.getOne(id);
        if(newTeeSheetUpdate.getTeetime() != null) {
            inDB.setTeetime(newTeeSheetUpdate.getTeetime());
        }
        if(newTeeSheetUpdate.getPlayer1() != null) {
            inDB.setPlayer1(newTeeSheetUpdate.getPlayer1());
        }
        if(newTeeSheetUpdate.getPlayer2() != null) {
            inDB.setPlayer2(newTeeSheetUpdate.getPlayer2());
        }
        if(newTeeSheetUpdate.getPlayer3() != null) {
            inDB.setPlayer3(newTeeSheetUpdate.getPlayer3());
        }
        if(newTeeSheetUpdate.getPlayer4() != null) {
            inDB.setPlayer4(newTeeSheetUpdate.getPlayer4());
        }
        newTeeSheetRepository.save(inDB);

        return new GenericResponse("Tee sheet updated");
    }
}

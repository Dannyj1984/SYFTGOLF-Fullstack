package com.syftgolf.syftgolf.event.teesheet;

import com.syftgolf.syftgolf.event.Event;
import com.syftgolf.syftgolf.event.EventRepository;
import com.syftgolf.syftgolf.event.EventService;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/1.0")
@CrossOrigin
public class TeeSheetController {

    @Autowired
    TeeSheetService teeSheetService;

    @Autowired
    TeeSheetRepository teeSheetRepository;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/event/getTeeSheet/{id:[0-9]+}")
    @CrossOrigin
    public Optional<TeeSheet> getTeeSheet(@PathVariable final long id){
        return teeSheetRepository.findById(id);
    }




}

package com.hoaxify.hoaxify.event;

import com.hoaxify.hoaxify.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class EntrantController {

    @Autowired
    EntrantService entrantService;

    @Autowired
    EntrantRepository entrantRepository;

    @PostMapping("/management/events/entrants")
    GenericResponse createEntrant(@Valid @RequestBody Entrants entrant) {
        System.out.println(entrant);
        entrantService.save(entrant);
        return new GenericResponse("Entrant saved");
    }
}

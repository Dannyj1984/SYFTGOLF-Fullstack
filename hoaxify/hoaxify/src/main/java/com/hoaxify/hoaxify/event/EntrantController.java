package com.hoaxify.hoaxify.event;

import com.hoaxify.hoaxify.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/1.0")
public class EntrantController {

    @Autowired
    EntrantService entrantService;

    @Autowired
    EntrantRepository entrantRepository;

    @PostMapping("/management/events/entrants")
    GenericResponse createEntrant(@Valid @RequestBody EntrantsDto entrant) {
        System.out.println("entrant is: " +entrant);
        entrantService.save(entrant);
        return new GenericResponse("Entrant saved");
    }


}

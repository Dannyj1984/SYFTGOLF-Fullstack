package com.syftgolf.syftgolf.event;

import com.syftgolf.syftgolf.course.Hole;
import com.syftgolf.syftgolf.shared.GenericResponse;
import com.syftgolf.syftgolf.user.User;
import com.syftgolf.syftgolf.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/1.0")
public class EntrantController {

    @Autowired
    EntrantService entrantService;

    @Autowired
    EntrantRepository entrantRepository;



    //Add a user to an event as an entrants
    @PostMapping("/management/events/entrants")
    @CrossOrigin
    GenericResponse createEntrant(@Valid @RequestBody EntrantsDto entrant) {
        entrantService.save(entrant);

        return  new GenericResponse("Entrant Saved");
    }


}

package com.syftgolf.syftgolf.society;

import com.syftgolf.syftgolf.event.Event;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1.0")
public class SocietyController {

    @Autowired
    SocietyRepository societyRepository;

    @Autowired
    SocietyService societyService;


    //Create a new society
    @PostMapping("/management/society")
    GenericResponse createSociety(@Valid @RequestBody Society society) {
        societyService.save(society);
        return new GenericResponse("Society saved");
    }

    //Get a list of societies
    @GetMapping("/society")
    List<Society> getSocieties() {
        return societyRepository.findAll();

    }
}

package com.syftgolf.syftgolf.society;

import com.syftgolf.syftgolf.event.Event;
import com.syftgolf.syftgolf.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class SocietyController {

    @Autowired
    SocietyRepository societyRepository;

    @Autowired
    SocietyService societyService;

    @PostMapping("/management/society")
    GenericResponse createSociety(@Valid @RequestBody Society society) {
        societyService.save(society);
        return new GenericResponse("Society saved");
    }
}

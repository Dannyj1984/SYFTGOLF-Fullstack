package com.hoaxify.hoaxify.course;

import com.hoaxify.hoaxify.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1.0")
public class HoleController {

    @Autowired
    HoleService holeService;

    @Autowired
    HoleRepository holeRepository;


    @PostMapping("/management/holes")
    GenericResponse createCourse(@Valid @RequestBody List<Hole> holes) {
        for(int i = 0; i < holes.size(); i++) {
            holeRepository.save(holes.get(i));
            System.out.println(holes.get(i));
        }
        return new GenericResponse("Hole saved");
    }
}

package com.syftgolf.syftgolf.course;

import com.syftgolf.syftgolf.shared.GenericResponse;
import com.syftgolf.syftgolf.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getListOfHoles/{courseid}")
    List<Hole> holes(@PathVariable long courseid){
        return holeRepository.findAllHoles(courseid);
    }
}

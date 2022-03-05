package com.syftgolf.syftgolf.service;

import com.syftgolf.syftgolf.entity.Course;
import com.syftgolf.syftgolf.entity.Hole;
import com.syftgolf.syftgolf.entity.vm.hole.HoleVM;
import com.syftgolf.syftgolf.repository.CourseRepo;
import com.syftgolf.syftgolf.repository.HoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoleService {

    /**
     * Save a new hole
     *  Update a hole
     *  find by course
     *  Delete a hole by ID
     */

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    HoleRepo holeRepo;

    public void save(long id, List<Hole> holes) {
        Course c = courseRepo.findById(id);

        for (Hole hole : holes) {
            hole.setCourse(c);
            List<Hole> courseHoles = c.getHoles();
            courseHoles.add(hole);
            hole.setHoleIdentifier(String.valueOf(id) + hole.getHoleNumber() + hole.getStrokeIndex());
            holeRepo.save(hole);
            courseRepo.save(c);
        }
//        hole.setCourse(c);
//        List<Hole> holes = c.getHoles();
//        holes.add(hole);
//        c.setHoles(holes);
//        hole.setHoleIdentifier(String.valueOf(id) + hole.getHoleNumber() + hole.getStrokeIndex());
//        holeRepo.save(hole);
//        courseRepo.save(c);
//        Course co = courseRepo.findCourseById(id);
//        System.out.println(co.getHoles());
    }

    public void update(long holeId, HoleVM holeVM) {
        Hole h = holeRepo.getById(holeId);
        h.setHoleNumber(holeVM.getHoleNumber());
        h.setPar(holeVM.getPar());
        h.setStrokeIndex(holeVM.getStrokeIndex());
        h.setYards(holeVM.getYards());
        h.setHoleIdentifier(String.valueOf(h.getCourse().getId()) + h.getHoleNumber() + h.getStrokeIndex());
        holeRepo.save(h);
    }


    public List<Hole> findByCourse(long courseId) {
        Course c = courseRepo.findCourseById(courseId);
        System.out.println(c.getHoles());
        return c.getHoles();
    }
}

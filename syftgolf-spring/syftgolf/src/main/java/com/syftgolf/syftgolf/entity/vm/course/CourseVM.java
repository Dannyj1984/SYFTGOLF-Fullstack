package com.syftgolf.syftgolf.entity.vm.course;

import com.syftgolf.syftgolf.entity.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CourseVM {


    private String name;


    private int par;


    private double slopeRating;


    private double courseRating;

    public CourseVM(Course course) {
        this.setName(course.getName());
        this.setPar(course.getPar());
        this.setSlopeRating(course.getSlopeRating());
        this.setCourseRating(course.getCourseRating());
    }
}

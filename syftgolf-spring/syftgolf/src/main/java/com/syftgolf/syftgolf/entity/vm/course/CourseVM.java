package com.syftgolf.syftgolf.entity.vm.course;

import com.syftgolf.syftgolf.entity.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CourseVM {

    private long id;

    private String name;

    private int par;

    private String postcode;

    private double slopeRating;

    private String country;

    private double courseRating;

    public CourseVM(Course course) {
        this.setId(course.getId());
        this.setName(course.getName());
        this.setPar(course.getPar());
        this.setPostcode(course.getPostcode());
        this.setCountry(course.getCountry());
        this.setSlopeRating(course.getSlopeRating());
        this.setCourseRating(course.getCourseRating());
    }
}

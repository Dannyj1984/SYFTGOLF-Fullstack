package com.syftgolf.syftgolf.course.vm;

import com.syftgolf.syftgolf.course.Course;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseVM {

    private Long id;

    private String courseName;

    private String postcode;

    private int par;

    private double courseRating;

    private int slopeRating;

    private String image;

    public CourseVM(Course course){
        this.setId(course.getCourseid());
        this.setImage(course.getImage());
        this.setCourseName(course.getCourseName());
        this.setPostcode(course.getPostCode());
        this.setSlopeRating(course.getSlopeRating());
        this.setCourseRating(course.getCourseRating());
        this.setPar(course.getPar());
    }
}
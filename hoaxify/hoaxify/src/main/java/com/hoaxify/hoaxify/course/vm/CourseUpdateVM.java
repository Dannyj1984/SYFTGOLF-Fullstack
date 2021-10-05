package com.hoaxify.hoaxify.course.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CourseUpdateVM {

    @NotNull
    @Size(min=4, max=255)
    private String courseName;

    @NotNull
    private int par;

    @NotNull
    private double courseRating;

    @NotNull
    private int slopeRating;

    private String image;
}

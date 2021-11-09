package com.syftgolf.syftgolf.course;


import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCoursenameValidator implements ConstraintValidator<UniqueCourseName, String> {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public boolean isValid(String courseName, ConstraintValidatorContext constraintValidatorContext) {
        Course inDB = courseRepository.findByCourseName(courseName);
        if(inDB == null) {
            return true;
        }
        return false;
    }
}

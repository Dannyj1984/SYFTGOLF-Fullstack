package com.hoaxify.hoaxify.event;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEventnameValidator implements ConstraintValidator<UniqueEventName, String> {

    @Autowired
    EventRepository eventRepository;

    @Override
    public boolean isValid(String eventName, ConstraintValidatorContext constraintValidatorContext) {
        Event inDB = eventRepository.findByEventname(eventName);
        if(inDB == null) {
            return true;
        }
        return false;
    }
}

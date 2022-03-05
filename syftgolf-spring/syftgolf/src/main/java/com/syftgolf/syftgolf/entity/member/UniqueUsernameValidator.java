package com.syftgolf.syftgolf.entity.member;

import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    MemberRepo memberRepo;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        Member inDB = memberRepo.findMemberByUsername(username);
        return inDB == null;
    }
}

package com.syftgolf.syftgolf.entity.member;

import com.syftgolf.syftgolf.entity.Member;
import com.syftgolf.syftgolf.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    MemberRepo memberRepo;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        Member inDB = memberRepo.findMemberByEmail(email);
        return inDB == null;
    }
}

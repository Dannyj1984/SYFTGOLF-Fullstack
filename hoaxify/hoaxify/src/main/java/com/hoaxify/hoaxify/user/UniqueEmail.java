package com.hoaxify.hoaxify.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "{hoaxify.constraints.username.UniqueEmail.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

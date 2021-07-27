package com.hoaxify.hoaxify.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueUsernameValidator.class)
//Define where annotation can be used
@Target(ElementType.FIELD)
//Tell JVM to let annotation be processed in runtime
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

    String message() default "{hoaxify.constraints.username.UniqueUsername.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

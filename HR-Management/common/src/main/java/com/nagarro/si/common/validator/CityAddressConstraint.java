package com.nagarro.si.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CityAddressValidator.class)
public @interface CityAddressConstraint {
    String message() default "City and address must either both be present or both be absent";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
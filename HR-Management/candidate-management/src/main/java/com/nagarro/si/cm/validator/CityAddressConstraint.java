package com.nagarro.si.cm.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to ensure that city and address fields are either both present or both absent.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CityAddressValidator.class)
public @interface CityAddressConstraint {
    String message() default "City and address must either both be present or both be absent";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
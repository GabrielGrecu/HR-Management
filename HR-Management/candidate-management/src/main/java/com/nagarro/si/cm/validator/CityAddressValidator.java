package com.nagarro.si.cm.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.nagarro.si.cm.dto.CandidateDto;

/**
 * Validator class to check that city and address fields are either both present or both absent.
 */
public class CityAddressValidator implements ConstraintValidator<CityAddressConstraint, CandidateDto> {

    @Override
    public void initialize(CityAddressConstraint constraintAnnotation) {
        // No initialization needed for this constraint annotation
    }

    @Override
    public boolean isValid(CandidateDto candidateDto, ConstraintValidatorContext context) {
        if (candidateDto == null) {
            return true; // Consider null objects as valid; adjust if needed
        }

        boolean cityIsPresent = candidateDto.getCity() != null;
        boolean addressIsPresent = candidateDto.getAddress() != null;

        if (cityIsPresent && !addressIsPresent || !cityIsPresent && addressIsPresent) {
            // Adding custom message
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
package com.nagarro.si.common.validator;

import com.nagarro.si.common.dto.CandidateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CityAddressValidator implements ConstraintValidator<CityAddressConstraint, CandidateDto> {

    @Override
    public boolean isValid(CandidateDto candidateDto, ConstraintValidatorContext context) {
        if (candidateDto == null) {
            return true;
        }

        boolean cityIsPresent = candidateDto.getCity() != null;
        boolean addressIsPresent = candidateDto.getAddress() != null;

        if (cityIsPresent && !addressIsPresent || !cityIsPresent && addressIsPresent) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
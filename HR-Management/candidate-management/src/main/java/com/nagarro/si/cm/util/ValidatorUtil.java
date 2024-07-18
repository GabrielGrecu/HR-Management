package com.nagarro.si.cm.util;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.validation.EmailValidator;
import com.nagarro.si.cm.validation.PhoneNumberValidator;
import org.springframework.stereotype.Component;

@Component
public class ValidatorUtil {

    private final EmailValidator emailValidator;
    private final PhoneNumberValidator phoneNumberValidator;

    public ValidatorUtil() {
        emailValidator = new EmailValidator();
        phoneNumberValidator = new PhoneNumberValidator();
    }

    public void validate(CandidateDto candidateDto) {
        if (!emailValidator.validateData(candidateDto.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!phoneNumberValidator.validateData(candidateDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }
}

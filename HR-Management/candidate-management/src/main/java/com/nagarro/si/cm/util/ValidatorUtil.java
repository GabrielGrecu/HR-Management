package com.nagarro.si.cm.util;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.validation.EmailValidator;
import com.nagarro.si.cm.validation.PhoneNumberValidator;

public class ValidatorUtil {

    private static final EmailValidator emailValidator = new EmailValidator();
    private static final PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();

    public static void validate(CandidateDto candidateDto) {
        if (!emailValidator.validateData(candidateDto.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!phoneNumberValidator.validateData(candidateDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }
}

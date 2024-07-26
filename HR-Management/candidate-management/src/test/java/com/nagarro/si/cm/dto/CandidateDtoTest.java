package com.nagarro.si.cm.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

public class CandidateDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCityAndAddress() {
        CandidateDto dto = new CandidateDto();
        dto.setCity("New York");
        dto.setAddress("123 Main St");
        dto.setUsername("user1");
        dto.setEmail("user1@example.com");
        dto.setBirthday(LocalDate.now());
        dto.setYearsOfExperience(5);

        Set<ConstraintViolation<CandidateDto>> violations = validator.validate(dto);

        for (ConstraintViolation<CandidateDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidCityWithoutAddress() {
        CandidateDto dto = new CandidateDto();
        dto.setCity("New York");
        dto.setUsername("user2");
        dto.setEmail("user2@example.com");
        dto.setBirthday(LocalDate.now());
        dto.setYearsOfExperience(5);
        Set<ConstraintViolation<CandidateDto>> violations = validator.validate(dto);
        for (ConstraintViolation<CandidateDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidAddressWithoutCity() {
        CandidateDto dto = new CandidateDto();
        dto.setAddress("123 Main St");
        dto.setUsername("user3");
        dto.setEmail("user3@example.com");
        dto.setBirthday(LocalDate.now());
        dto.setYearsOfExperience(5);

        Set<ConstraintViolation<CandidateDto>> violations = validator.validate(dto);

        for (ConstraintViolation<CandidateDto> violation : violations) {
            System.out.println(violation.getMessage());
        }

        assertFalse(violations.isEmpty());
    }
}
package com.nagarro.si.cm.dto;

import com.nagarro.si.cm.validator.ValidationGroups;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CandidateDtoTest {

    @Autowired
    private LocalValidatorFactoryBean validatorFactory;

    @ParameterizedTest
    @CsvSource({
            // id, username, birthday, email, city, address, faculty, phoneNumber, yearsOfExperience, recruitmentChannel, expectedViolationCount
            "1, , 2000-01-01, user1@example.com, city1, address1, Science, +1234567890, 5, LinkedIn, 1",//Username is null
            "2, user1, 2025-01-01, user2@example.com, city2, address2, Science, +1234567890, 5, LinkedIn, 1",//Birthday is invalid
            "3, user2, 2000-01-01, , city3, address3, Science, +1234567890, 5, LinkedIn, 1",//Email is null
            "4, user3, 2000-01-01, invalidemail, city4, address4, Science, +1234567890, 5, LinkedIn, 1",//Email is invalid
            "5, user4, 2000-01-01, user4@example.com, city5, address5, Science, 12345, 5, LinkedIn, 1",//PhoneNumber is invalid
            "6, user5, 2000-01-01, user5@example.com, city6, address6, Science, +1234567890, , LinkedIn, 1",//YearsOfExperience is null
            "7, user6, 2000-01-01, user6@example.com, , address7, Science, +1234567890, 5, LinkedIn, 1",//City is null,Address is not absent
            "7, user7, 2000-01-01, user7@example.com, city7, , Science, +1234567890, 5, LinkedIn, 1",//Address is null ,City is not absent
            "7, user7, 2000-01-01, user7@example.com, , , Science, +1234567890, 5, LinkedIn, 0",//Address is null ,City null
            "9, user8, 2000-01-01, user8@example.com, city8, address8, Science, +1234567890, 5, LinkedIn, 0"//Valid
    })
    void testCandidateDtoValidations(int id, String username, LocalDate birthday, String email, String city,
                                     String address, String faculty, String phoneNumber, Integer yearsOfExperience,
                                     String recruitmentChannel, int expectedViolationCount) {

        CandidateDto candidate = new CandidateDto(id, username, birthday, email, city, address, faculty, phoneNumber, yearsOfExperience, recruitmentChannel);

        Set<ConstraintViolation<CandidateDto>> violations = validatorFactory.getValidator().validate(candidate, ValidationGroups.ValidateUpdate.class);

        assertEquals(expectedViolationCount, violations.size());
    }


}

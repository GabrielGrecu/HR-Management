package com.nagarro.si.cm.dto;

import com.nagarro.si.cm.CandidateManagementApplication;
import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.common.dto.Status;
import com.nagarro.si.common.validator.ValidationGroups;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CandidateManagementApplication.class)
public class CandidateDtoTest {

    @Autowired
    private LocalValidatorFactoryBean validatorFactory;


//    @ParameterizedTest
    @MethodSource("candidateDtoProvider")
    void testCandidateDtoValidations(int id, String username, LocalDate birthday, String email, String city,
                                     String address, String faculty, String phoneNumber, Integer yearsOfExperience,
                                     String recruitmentChannel, Status candidateStatus, Date statusDate, Integer jobId,
                                     int expectedViolationCount) {

        CandidateDto candidate = new CandidateDto(id, username, birthday, email, city, address, faculty, phoneNumber,
                yearsOfExperience, recruitmentChannel, candidateStatus, statusDate, jobId);

        Set<ConstraintViolation<CandidateDto>> violations = validatorFactory.getValidator().validate(candidate, ValidationGroups.ValidateUpdate.class);

        assertEquals(expectedViolationCount, violations.size());
    }

    private static Stream<Arguments> candidateDtoProvider() {
        return Stream.of(
                Arguments.of(1, "", LocalDate.of(2000, 1, 1), "user1@example.com", "city1", "address1", "Science", "+1234567890", 5, "LinkedIn", Status.IN_PROGRESS, Date.valueOf(LocalDate.now()), 1, 1)  // Username is blank
        );
    }
}

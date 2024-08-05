package com.nagarro.si.cm.dto;

import com.nagarro.si.cm.CandidateManagementApplication;
import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.common.validator.ValidationGroups;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CandidateManagementApplication.class)
public class CandidateDtoTest {

    @Autowired
    private LocalValidatorFactoryBean validatorFactory;

    @ParameterizedTest
    @MethodSource("candidateDtoProvider")
    void testCandidateDtoValidations(int id, String username, LocalDate birthday, String email, String city,
                                     String address, String phoneNumber, Integer jobId, int expectedViolationCount) {

        CandidateDto candidate = new CandidateDto(id, username, birthday, email, city, address, phoneNumber, jobId);

        Set<ConstraintViolation<CandidateDto>> violations = validatorFactory.getValidator().validate(candidate, ValidationGroups.ValidateUpdate.class);

        assertEquals(expectedViolationCount, violations.size());
    }

    private static Stream<Arguments> candidateDtoProvider() {
        return Stream.of(
                Arguments.of(1, "", LocalDate.of(2000, 1, 1), "user1@example.com", "city1", "address1", "+1234567890", 1, 1),  // Username is blank
                Arguments.of(2, "user2", LocalDate.of(2025, 1, 1), "user2@example.com", "city2", "address2", "+1234567890", 1, 1),  // Birthday is in the future
                Arguments.of(3, "user3", LocalDate.of(2000, 1, 1), "", "city3", "address3", "+1234567890", 1, 1),  // Email is blank
                Arguments.of(4, "user4", LocalDate.of(2000, 1, 1), "invalidemail", "city4", "address4", "+1234567890", 1, 1),  // Email is invalid
                Arguments.of(5, "user5", LocalDate.of(2000, 1, 1), "user5@example.com", "city5", "address5", "12345", 1, 1),  // Phone number is invalid
                Arguments.of(6, "user6", LocalDate.of(2000, 1, 1), "user6@example.com", "city6", "address6", "+1234567890", null, 1),  // Job ID is null
                Arguments.of(7, "user7", LocalDate.of(2000, 1, 1), "user7@example.com", null, "address7", "+1234567890", 1, 1),  // City is null
                Arguments.of(8, "user8", LocalDate.of(2000, 1, 1), "user8@example.com", "city8", null, "+1234567890", 1, 1),  // Address is null
                Arguments.of(9, "user9", LocalDate.of(2000, 1, 1), "user9@example.com", null, null, "+1234567890", 1, 0),  // City and Address are null: Valid
                Arguments.of(10, "user10", LocalDate.of(2000, 1, 1), "user10@example.com", "city10", "address10", "+1234567890", 1, 0)  // Valid
        );
    }
}

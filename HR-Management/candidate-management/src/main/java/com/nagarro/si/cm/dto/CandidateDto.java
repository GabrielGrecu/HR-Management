package com.nagarro.si.cm.dto;

import com.nagarro.si.cm.entity.Status;
import com.nagarro.si.cm.validator.CityAddressConstraint;
import com.nagarro.si.cm.validator.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@CityAddressConstraint(groups = {ValidationGroups.ValidateUpdate.class, ValidationGroups.ValidatePatch.class})
public class CandidateDto {
    private int id;

    @NotBlank(message = "Username is required", groups = ValidationGroups.ValidateUpdate.class)
    private String username;

    @PastOrPresent(message = "Birthday must be in the past or present", groups = ValidationGroups.ValidateUpdate.class)
    @NotNull(message = "Birthday is required", groups = ValidationGroups.ValidateUpdate.class)
    private LocalDate birthday;

    @Email(message = "Email should be valid", groups = {ValidationGroups.ValidateUpdate.class, ValidationGroups.ValidatePatch.class})
    @NotBlank(message = "Email is required", groups = ValidationGroups.ValidateUpdate.class)
    private String email;

    private String city;
    private String address;
    private String faculty;

    @Pattern(regexp = "^([+]|0)\\d{1,14}$", message = "Phone number is not valid", groups = {ValidationGroups.ValidateUpdate.class, ValidationGroups.ValidatePatch.class})
    private String phoneNumber;

    @NotNull(message = "Years of experience is required", groups = ValidationGroups.ValidateUpdate.class)
    private Integer yearsOfExperience;

    private String recruitmentChannel;

    private String CandidateStatus;

    private String CandidateFeedback;
}

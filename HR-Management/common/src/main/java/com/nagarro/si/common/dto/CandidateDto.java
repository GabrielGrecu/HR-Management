package com.nagarro.si.common.dto;

import com.nagarro.si.common.validator.CityAddressConstraint;
import com.nagarro.si.common.validator.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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


    private Integer yearsOfExperience = 0;

    private String recruitmentChannel;

    private Status candidateStatus = Status.IN_PROGRESS;

    private Date statusDate;

    private List<FeedbackDto> feedbacks = new ArrayList<>();

    @NotNull(message = "Job ID is required", groups = ValidationGroups.ValidateUpdate.class)
    private Integer jobId;

    public CandidateDto(int id, String username, LocalDate birthday, String email, String city, String address, String phoneNumber, Integer jobId) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.email = email;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.jobId = jobId;
    }
}

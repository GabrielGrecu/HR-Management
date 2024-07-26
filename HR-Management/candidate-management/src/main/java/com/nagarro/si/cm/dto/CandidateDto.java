package com.nagarro.si.cm.dto;

import com.nagarro.si.cm.validator.CityAddressConstraint;
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
@CityAddressConstraint
public class CandidateDto {
    private int id;

    @NotBlank(message = "Username is required")
    private String username;

    @PastOrPresent(message = "Birthday must be in the past or present")
    @NotNull(message = "Birthday is required")
    private LocalDate birthday;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    private String city;

    private String address;

    private String faculty;

    @Pattern(regexp = "^([+]|0)\\d{1,14}$", message = "Phone number is not valid")
    private String phoneNumber;

    @NotNull(message = "Years of experience is required")
    private Integer yearsOfExperience;

    private String recruitmentChannel;
}
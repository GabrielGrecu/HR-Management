package com.nagarro.si.cm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CandidateDto {
    private int id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Birthday is required")
    private String birthday;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    private String city;

    private String faculty;

    @Pattern(regexp = "^([+]|0)\\d{1,14}$", message = "Phone number is not valid")
    private String phoneNumber;

    @NotNull(message = "Years of experience is required")
    private Integer yearsOfExperience;

    private String recruitmentChannel;
}

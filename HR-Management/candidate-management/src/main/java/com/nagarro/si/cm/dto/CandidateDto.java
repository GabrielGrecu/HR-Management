package com.nagarro.si.cm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CandidateDto {

    private String username;
    private String birthday;
    private String email;
    private String city;
    private String faculty;
    private String phoneNumber;
    private int yearsOfExperience;
    private String recruitmentChannel;
}

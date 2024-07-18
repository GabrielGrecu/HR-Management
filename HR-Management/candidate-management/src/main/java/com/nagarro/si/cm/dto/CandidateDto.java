package com.nagarro.si.cm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CandidateDto {
    private int id;
    private String username;
    private String birthday;
    private String email;
    private String city;
    private String faculty;
    private String phoneNumber;
    private int yearsOfExperience;
    private String recruitmentChannel;
}

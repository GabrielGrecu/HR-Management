package com.nagarro.si.cm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CandidateCreationDto {

    private String username;
    private Date birthDay;
    private String email;
    private String city;
    private String faculty;
    private String phoneNumber;
    private int yearsOfExperience;
    private String recruitmentChannel;
}

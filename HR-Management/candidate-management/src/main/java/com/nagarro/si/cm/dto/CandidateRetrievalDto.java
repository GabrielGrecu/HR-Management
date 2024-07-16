package com.nagarro.si.cm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CandidateRetrievalDto {

    private String username;
    private String email;
    private String phoneNumber;
}

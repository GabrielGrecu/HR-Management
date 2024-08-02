package com.nagarro.si.cm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobDto {

    private int id;

    @NotBlank(message = "Job title is required")
    private String title;
}

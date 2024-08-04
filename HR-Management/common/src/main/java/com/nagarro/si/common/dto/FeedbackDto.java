package com.nagarro.si.common.dto;

import com.nagarro.si.common.entity.FeedbackStatus;
import com.nagarro.si.common.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedbackDto {
    private int id;

    @NotBlank
    private String comment;

    @NotNull
    private UserRole role;

    @NotNull
    private FeedbackStatus status;

    @NotNull
    private int candidateId;
}

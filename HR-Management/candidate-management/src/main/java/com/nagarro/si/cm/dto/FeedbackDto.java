package com.nagarro.si.cm.dto;

import com.nagarro.si.cm.entity.FeedbackStatus;
import com.nagarro.si.cm.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedbackDto {
    @NotNull
    private int id;

    @NotBlank
    private String comment;

    @NotNull
    private UserRole role;

    @NotNull
    private FeedbackStatus status;

}

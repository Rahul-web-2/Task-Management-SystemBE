package com.TaskManagementSystem.DTO.Request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "Project name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;

    @NotNull(message = "User list is required")
    @Size(min = 1, message = "At least one user must be assigned")
    private List<Long> userIds;
}
package com.TaskManagementSystem.DTO.Request;

import com.TaskManagementSystem.Model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z @#$%^&*()_+=!.\\-]+$",
            message = "Title can only contain letters and special characters (@#$%^&*()_+=!.-)"
    )
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required")
    private Task.TaskStatus status;

    @NotNull(message = "Priority is required")
    private Task.TaskPriority priority;

    private Long assignedToId;
    private Long projectId;
}
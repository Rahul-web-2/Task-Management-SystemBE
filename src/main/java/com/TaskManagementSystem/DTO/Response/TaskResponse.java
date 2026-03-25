package com.TaskManagementSystem.DTO.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;

    private Long assignedToId;
    private String assignedToName;

    private Long createdById;
    private String createdByName;

    private Long projectId;
    private String projectName;

    private LocalDateTime createdAt;
}

package com.TaskManagementSystem.DTO.Response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private Long id;

    private String name;
    private String description;
    private String status;
    private LocalDate dueDate;

    private LocalDate createdAt;

    private Long createdById;
    private String createdByName;

    private List<Long> userIds;
}
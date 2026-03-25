package com.TaskManagementSystem.DTO.Request;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private String title;
    private String description;
    private String status;
    private String priority;

    private Long assignedToId;
    private Long projectId;

}

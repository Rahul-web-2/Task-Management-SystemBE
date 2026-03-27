package com.TaskManagementSystem.Mapper;

import com.TaskManagementSystem.DTO.Request.TaskRequest;
import com.TaskManagementSystem.DTO.Response.TaskResponse;
import com.TaskManagementSystem.Model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    // ================= DTO → ENTITY =================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task toEntity(TaskRequest taskRequest);

    // ================= ENTITY → DTO =================

    @Mapping(target = "assignedToId",
            expression = "java(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null)")
    @Mapping(target = "assignedToName",
            expression = "java(task.getAssignedTo() != null ? task.getAssignedTo().getName() : null)")

    @Mapping(target = "createdById",
            expression = "java(task.getCreatedBy() != null ? task.getCreatedBy().getId() : null)")
    @Mapping(target = "createdByName",
            expression = "java(task.getCreatedBy() != null ? task.getCreatedBy().getName() : null)")

    @Mapping(target = "projectId",
            expression = "java(task.getProject() != null ? task.getProject().getId() : null)")
    @Mapping(target = "projectName",
            expression = "java(task.getProject() != null ? task.getProject().getName() : null)")

    TaskResponse toResponseDTO(Task task);
}
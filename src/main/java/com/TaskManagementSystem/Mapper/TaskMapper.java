package com.TaskManagementSystem.Mapper;

import com.TaskManagementSystem.DTO.Request.TaskRequest;
import com.TaskManagementSystem.DTO.Response.TaskResponse;
import com.TaskManagementSystem.Model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    // ✅ DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Task toEntity(TaskRequest taskRequest);

    // ✅ Entity → DTO
    @Mapping(target = "assignedToId", source = "assignedTo.id")
    @Mapping(target = "assignedToName", source = "assignedTo.name")

    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "createdByName", source = "createdBy.name")

    @Mapping(target = "projectId",
            expression = "java(task.getProject() != null ? task.getProject().getId() : null)")
    @Mapping(target = "projectName",
            expression = "java(task.getProject() != null ? task.getProject().getName() : null)")
    TaskResponse toResponseDTO(Task task);
}

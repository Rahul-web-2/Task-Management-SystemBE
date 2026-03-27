package com.TaskManagementSystem.Mapper;

import com.TaskManagementSystem.DTO.Request.ProjectRequest;
import com.TaskManagementSystem.DTO.Response.ProjectResponse;
import com.TaskManagementSystem.Model.Project;
import com.TaskManagementSystem.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    // DTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "users", ignore = true) // handled in service
    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectRequest projectRequest);

    // Entity → DTO
    @Mapping(target = "createdById", source = "createdBy.id")
    @Mapping(target = "createdByName", source = "createdBy.name")
    @Mapping(target = "userIds", expression = "java(project.getUsers() != null ? project.getUsers().stream().map(User::getId).toList() : null)")
    ProjectResponse toResponseDTO(Project project);
}
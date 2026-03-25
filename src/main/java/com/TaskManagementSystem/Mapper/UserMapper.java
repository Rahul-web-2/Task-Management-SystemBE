package com.TaskManagementSystem.Mapper;

import com.TaskManagementSystem.DTO.Request.UserRequest;
import com.TaskManagementSystem.DTO.Response.UserResponse;
import com.TaskManagementSystem.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Define mapping methods here
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignedTasks", ignore = true)
    @Mapping(target = "createdTasks", ignore = true)
    User toEntity(UserRequest dto);
    UserResponse toResponseDTO(User user);

}

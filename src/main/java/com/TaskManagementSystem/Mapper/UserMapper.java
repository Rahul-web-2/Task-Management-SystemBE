package com.TaskManagementSystem.Mapper;

import com.TaskManagementSystem.DTO.Request.UserRequest;
import com.TaskManagementSystem.DTO.Response.UserResponse;
import com.TaskManagementSystem.Model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Define mapping methods here
    User toEntity(UserRequest dto);
    UserResponse toResponseDTO(User user);

}

package com.TaskManagementSystem.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private UserResponse user;
    private String token;

}

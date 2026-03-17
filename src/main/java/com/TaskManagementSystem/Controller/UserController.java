package com.TaskManagementSystem.Controller;

import com.TaskManagementSystem.DTO.Request.LoginRequest;
import com.TaskManagementSystem.DTO.Request.UserRequest;
import com.TaskManagementSystem.DTO.Response.LoginResponse;
import com.TaskManagementSystem.DTO.Response.UserResponse;
import com.TaskManagementSystem.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/SignUp")
    public ResponseEntity<LoginResponse> createUser(@RequestBody UserRequest request) {

        LoginResponse response = userService.createUser(request);

        // ✅ If user already exists → 409 Conflict
        if ("User already exists".equals(response.getMessage())) {
            return ResponseEntity.status(409).body(response);
        }

        // ✅ If new user created → 201 Created
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/Login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = userService.loginUser(request);

        return switch (response.getMessage()) {
            case "USER_NOT_FOUND" -> ResponseEntity.status(404).body(response);
            case "INVALID_CREDENTIALS" -> ResponseEntity.status(401).body(response);
            default -> ResponseEntity.ok(response);
        };
    }

}

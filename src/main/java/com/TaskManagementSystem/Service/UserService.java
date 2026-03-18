package com.TaskManagementSystem.Service;

import com.TaskManagementSystem.DTO.Request.LoginRequest;
import com.TaskManagementSystem.DTO.Request.UserRequest;
import com.TaskManagementSystem.DTO.Response.LoginResponse;
import com.TaskManagementSystem.Model.User;
import com.TaskManagementSystem.ModelMapper.UserMapper;
import com.TaskManagementSystem.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // ── Sign Up ──────────────────────────────────────────────────────────────
    public LoginResponse createUser(UserRequest requestDTO) {

        Optional<User> existingUser = userRepo.findByEmail(requestDTO.getEmail());
        if (existingUser.isPresent()) {
            return new LoginResponse("User already exists", userMapper.toResponseDTO(existingUser.get()));
        }

        User user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user = userRepo.save(user);

        return new LoginResponse("User created successfully", userMapper.toResponseDTO(user));
    }

    // ── Login ────────────────────────────────────────────────────────────────
    public LoginResponse loginUser(LoginRequest requestDTO) {

        Optional<User> optionalUser = userRepo.findByEmail(requestDTO.getEmail());

        if (optionalUser.isEmpty()) {
            return new LoginResponse("USER_NOT_FOUND", null);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            return new LoginResponse("INVALID_CREDENTIALS", null);
        }

        return new LoginResponse("SUCCESS", userMapper.toResponseDTO(user));
    }
}
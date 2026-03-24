package com.TaskManagementSystem.Service;

import com.TaskManagementSystem.Config.JWTUtils;
import com.TaskManagementSystem.DTO.Request.LoginRequest;
import com.TaskManagementSystem.DTO.Request.UserRequest;
import com.TaskManagementSystem.DTO.Response.LoginResponse;
import com.TaskManagementSystem.Model.User;
import com.TaskManagementSystem.Mapper.UserMapper;
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
    private final JWTUtils jwtUtils;

    // ✅ SIGNUP
    public LoginResponse createUser(UserRequest requestDTO) {

        Optional<User> existingUser = userRepo.findByEmail(requestDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("USER_ALREADY_EXISTS");
        }

        User user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user = userRepo.save(user);

        String token = jwtUtils.generateToken(user.getEmail());
        return new LoginResponse(
                "SUCCESS",
                userMapper.toResponseDTO(user),
                token
        );
    }

    // ✅ LOGIN
    public LoginResponse loginUser(LoginRequest requestDTO) {

        Optional<User> optionalUser = userRepo.findByEmail(requestDTO.getEmail());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("INVALID_CREDENTIALS");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return new LoginResponse(
                "SUCCESS",
                userMapper.toResponseDTO(user),
                token
        );
    }
}
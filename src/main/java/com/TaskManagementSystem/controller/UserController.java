package com.TaskManagementSystem.controller;

import com.TaskManagementSystem.model.User;
import com.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUsers(user);
    }

    @GetMapping()
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return userService.login(user.getEmail(), user.getPassword());
    }
}

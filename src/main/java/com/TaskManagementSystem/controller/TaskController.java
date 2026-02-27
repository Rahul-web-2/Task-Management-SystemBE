package com.TaskManagementSystem.controller;

import com.TaskManagementSystem.model.Task;
import com.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/user/{userEmail}")
    public Task createTask(@PathVariable String userEmail,
                           @RequestBody Task task) {
        return taskService.createTask(userEmail, task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}

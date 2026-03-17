package com.TaskManagementSystem.Controller;

import com.TaskManagementSystem.Model.Task;
import com.TaskManagementSystem.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/user/{userId}")
    public Task createTask(@PathVariable Long userId, @RequestBody Task task) {
        return taskService.createTask(userId, task);
    }

    @PostMapping("/update/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @GetMapping("/user/{email}")
    public List<Task> getTasksByUser(@PathVariable String email) {
        return taskService.getTasksByUser(email);
    }

}

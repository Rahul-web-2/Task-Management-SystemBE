package com.TaskManagementSystem.Controller;

import com.TaskManagementSystem.Model.Task;
import com.TaskManagementSystem.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // createTask now uses email (matches what the frontend sends)
    @PostMapping("/user/{email}")
    public Task createTask(@PathVariable String email, @RequestBody Task task) {
        return taskService.createTask(email, task);
    }

    // support both POST (old) and PUT (frontend uses PUT)
    @PutMapping("/update/{id}")
    public Task updateTaskPut(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/user/{email}")
    public List<Task> getTasksByUser(@PathVariable String email) {
        return taskService.getTasksByUser(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
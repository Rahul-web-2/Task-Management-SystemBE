package com.TaskManagementSystem.Controller;

import com.TaskManagementSystem.Model.Task;
import com.TaskManagementSystem.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ✅ Create task for logged-in user
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, Authentication auth) {
        String email = auth.getName(); // comes from JWT filter
        Task createdTask = taskService.createTask(email, task);
        return ResponseEntity.status(201).body(createdTask);
    }

    // ✅ Update task
    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @RequestBody Task updatedTask,
                                           Authentication auth) {
        String email = auth.getName();
        Task task = taskService.updateTask(id, updatedTask, email);
        return ResponseEntity.ok(task);
    }

    // ✅ Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, Authentication auth) {
        String email = auth.getName();
        Task task = taskService.getTaskById(id, email);
        return ResponseEntity.ok(task);
    }

    // ✅ Get all tasks for logged-in user
    @GetMapping
    public ResponseEntity<List<Task>> getTasksByUser(Authentication auth) {
        String email = auth.getName();
        List<Task> tasks = taskService.getTasksByUser(email);
        return ResponseEntity.ok(tasks);
    }

    // ✅ Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication auth) {
        String email = auth.getName();
        taskService.deleteTask(id, email);
        return ResponseEntity.noContent().build();
    }
}
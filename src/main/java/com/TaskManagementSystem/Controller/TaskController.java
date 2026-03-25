package com.TaskManagementSystem.Controller;

import com.TaskManagementSystem.DTO.Request.TaskRequest;
import com.TaskManagementSystem.DTO.Response.TaskResponse;
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

    // ✅ CREATE TASK
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request,
                                                   Authentication auth) {

        String email = auth.getName();
        TaskResponse response = taskService.createTask(request, email);

        return ResponseEntity.status(201).body(response);
    }

    // ✅ GET TASK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id,
                                                    Authentication auth) {

        String email = auth.getName();
        return ResponseEntity.ok(taskService.getTaskById(id, email));
    }

    // ✅ UPDATE TASK
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @RequestBody TaskRequest request,
                                                   Authentication auth) {

        String email = auth.getName();
        return ResponseEntity.ok(taskService.updateTask(id, request, email));
    }

    // ✅ GET ALL TASKS (personal + project)
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(Authentication auth) {

        String email = auth.getName();
        return ResponseEntity.ok(taskService.getAllTask(email));
    }

    // ✅ GET PERSONAL TASKS
    @GetMapping("/personal")
    public ResponseEntity<List<TaskResponse>> getPersonalTasks(Authentication auth) {

        String email = auth.getName();
        return ResponseEntity.ok(taskService.getPersonalTask(email));
    }

    // ✅ GET PROJECT TASKS
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(
            @PathVariable Long projectId,
            Authentication auth) {

        String email = auth.getName();
        return ResponseEntity.ok(
                taskService.getProjectTask(email, projectId)
        );
    }

    // ✅ DELETE TASK
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id,
                                           Authentication auth) {

        String email = auth.getName();
        taskService.deleteTask(id, email);
        return ResponseEntity.noContent().build();
    }
}
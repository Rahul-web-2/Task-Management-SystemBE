package com.TaskManagementSystem.Service;

import com.TaskManagementSystem.Model.Task;
import com.TaskManagementSystem.Model.User;
import com.TaskManagementSystem.Repository.TaskRepo;
import com.TaskManagementSystem.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepository;
    private final UserRepo userRepository;

    // ✅ Create a task for the logged-in user
    public Task createTask(String email, Task task) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        return taskRepository.save(task);
    }

    // ✅ Get a task by ID, ensure it belongs to the user
    public Task getTaskById(Long id, String email) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            return null;
        }

        Task task = optionalTask.get();
        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }

        return task;
    }

    // ✅ Update a task, ensure ownership
    public Task updateTask(Long id, Task updatedTask, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());

        return taskRepository.save(task);
    }

    // ✅ Get all tasks for the logged-in user
    public List<Task> getTasksByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(user);
    }

    // ✅ Delete a task, ensure ownership
    public void deleteTask(Long id, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Access denied");
        }
        taskRepository.delete(task);
    }
}
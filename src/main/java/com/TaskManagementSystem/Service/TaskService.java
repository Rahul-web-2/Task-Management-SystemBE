package com.TaskManagementSystem.Service;

import com.TaskManagementSystem.Model.Task;
import com.TaskManagementSystem.Model.User;
import com.TaskManagementSystem.Repository.TaskRepo;
import com.TaskManagementSystem.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepository;
    private final UserRepo userRepository;


    public Task createTask(Long userId, Task task) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask){
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task Not Found"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());

        return taskRepository.save(task);
    }

    public List<Task> getTasksByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(user);
    }
}

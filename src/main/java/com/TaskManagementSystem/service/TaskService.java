package com.TaskManagementSystem.service;

import com.TaskManagementSystem.model.Task;
import com.TaskManagementSystem.model.User;
import com.TaskManagementSystem.repository.TaskRepository;
import com.TaskManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


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

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    public Task getById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task Not Found"));
    }

    public List<Task> getTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUser_Id(userId);
        System.out.println("Tasks found: " + tasks.size());
        return tasks;
    }
}

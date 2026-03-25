package com.TaskManagementSystem.Service;

import com.TaskManagementSystem.DTO.Request.TaskRequest;
import com.TaskManagementSystem.DTO.Response.TaskResponse;
import com.TaskManagementSystem.Mapper.TaskMapper;
import com.TaskManagementSystem.Model.Project;
import com.TaskManagementSystem.Model.Task;
import com.TaskManagementSystem.Model.User;
import com.TaskManagementSystem.Repository.ProjectRepo;
import com.TaskManagementSystem.Repository.TaskRepo;
import com.TaskManagementSystem.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final TaskMapper taskMapper;

    private User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    private void validateAccess(Task task, String email) {
        boolean isAssignedUser = task.getAssignedTo().getEmail().equals(email);
        boolean isProjectHead = task.getProject() != null &&
                task.getProject().getCreatedBy().getEmail().equals(email);

        if (!isAssignedUser && !isProjectHead) {
            throw new RuntimeException("Access denied");
        }
    }

    // Create Task
    public TaskResponse createTask(TaskRequest requestDTO, String email) {
        User user = getUserByEmail(email);

        Task task = taskMapper.toEntity(requestDTO);
        task.setCreatedBy(user);

        User assignedUser = (requestDTO.getAssignedToId() !=null)
                ? userRepo.findById(requestDTO.getAssignedToId())
                . orElseThrow(()-> new RuntimeException("Assigned user not found"))
                :user;

        task.setAssignedTo(assignedUser);

        if(requestDTO.getProjectId() != null) {
            Project project = projectRepo.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            if (assignedUser.getProject() == null ||
                    !assignedUser.getProject().getId().equals(project.getId())) {
                throw new RuntimeException("User not part of this project");
            }

            task.setProject(project);
        }

       Task saveTask = taskRepo.save(task);
       return taskMapper.toResponseDTO(saveTask);
    }

    // Update Task
    public TaskResponse updateTask(Long id, TaskRequest requestDTO, String email) {
        Task task = getTaskOrThrow(id);
        validateAccess(task , email);

        task.setStatus(requestDTO.getStatus());

        Task updatedTask = taskRepo.save(task);
        return taskMapper.toResponseDTO(updatedTask);
    }

    // Get Task By ID
    public TaskResponse getTaskById(Long id, String email) {
        Task task = getTaskOrThrow(id);
        validateAccess(task , email);

        return taskMapper.toResponseDTO(task);
    }

    // Get Project Task
    public List<TaskResponse> getProjectTask(String email, long projectId) {
        User user = getUserByEmail(email);

        if (user.getProject() == null ||
                !user.getProject().getId().equals(projectId)) {
            throw new RuntimeException("Access denied");
        }

        return taskRepo.findByProjectId(projectId)
                .stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Get Personal Task
    public List<TaskResponse> getPersonalTask(String email){
        User user = getUserByEmail(email);

        return taskRepo.findByAssignedToAndProjectIsNull(user)
                .stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // GET ALL TASKS
    public List<TaskResponse> getAllTask(String email){
        User user = getUserByEmail(email);

        return taskRepo.findByAssignedTo(user)
                .stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Delete Task
    public void deleteTask(Long id, String email) {
        Task task = getTaskOrThrow(id);
        validateAccess(task, email);

        taskRepo.delete(task);
    }
}
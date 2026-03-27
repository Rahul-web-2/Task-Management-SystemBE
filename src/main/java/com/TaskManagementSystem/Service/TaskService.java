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

    // ================= HELPER METHODS =================

    private User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Task getTaskOrThrow(Long id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    private void validateAccess(Task task, String email) {

        boolean isAssignedUser = task.getAssignedTo() != null &&
                task.getAssignedTo().getEmail().equals(email);

        boolean isProjectHead = task.getProject() != null &&
                task.getProject().getCreatedBy() != null &&
                task.getProject().getCreatedBy().getEmail().equals(email);

        boolean isCreator = task.getCreatedBy() != null &&
                task.getCreatedBy().getEmail().equals(email);

        if (!isAssignedUser && !isProjectHead && !isCreator) {
            throw new RuntimeException("Access denied");
        }
    }

    // ================= CREATE TASK =================

    public TaskResponse createTask(TaskRequest requestDTO, String email) {

        User user = getUserByEmail(email);
        Task task = taskMapper.toEntity(requestDTO);

        // CASE 1: PERSONAL TASK
        if (requestDTO.getProjectId() == null) {

            task.setCreatedBy(user);
            task.setAssignedTo(user);   // 🔥 FIXED
            task.setProject(null);
        }

        // CASE 2: PROJECT TASK
        else {

            Project project = projectRepo.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            // Only project head can assign tasks
            if (project.getCreatedBy() == null ||
                    !project.getCreatedBy().getEmail().equals(email)) {
                throw new RuntimeException("Only the project head can assign tasks");
            }

            // Assigned user is required
            if (requestDTO.getAssignedToId() == null) {
                throw new RuntimeException("Assigned user is required for project tasks");
            }

            User assignedUser = userRepo.findById(requestDTO.getAssignedToId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));

            // Check user belongs to project
            if (assignedUser.getProject() == null ||
                    !assignedUser.getProject().getId().equals(project.getId())) {
                throw new RuntimeException("Assigned user is not a member of this project");
            }

            task.setCreatedBy(user);
            task.setAssignedTo(assignedUser);
            task.setProject(project);
        }

        Task savedTask = taskRepo.save(task);
        return taskMapper.toResponseDTO(savedTask);
    }

    // ================= UPDATE TASK =================

    public TaskResponse updateTask(Long id, TaskRequest requestDTO, String email) {
        Task task = getTaskOrThrow(id);
        validateAccess(task, email);

        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setPriority(requestDTO.getPriority());
        task.setStatus(requestDTO.getStatus());

        Task updatedTask = taskRepo.save(task);
        return taskMapper.toResponseDTO(updatedTask);
    }

    // ================= GET TASK BY ID =================

    public TaskResponse getTaskById(Long id, String email) {
        Task task = getTaskOrThrow(id);
        validateAccess(task, email);

        return taskMapper.toResponseDTO(task);
    }

    // ================= GET PROJECT TASKS =================

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

    // ================= GET PERSONAL TASKS =================

    public List<TaskResponse> getPersonalTask(String email) {
        User user = getUserByEmail(email);

        return taskRepo.findByAssignedToAndProjectIsNull(user)
                .stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ================= GET ALL TASKS =================

    public List<TaskResponse> getAllTask(String email) {
        User user = getUserByEmail(email);

        return taskRepo.findByAssignedTo(user)
                .stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ================= DELETE TASK =================

    public void deleteTask(Long id, String email) {
        Task task = getTaskOrThrow(id);
        validateAccess(task, email);

        taskRepo.delete(task);
    }
}
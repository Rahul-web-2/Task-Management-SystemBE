package com.TaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TaskManagementSystem.model.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser_Id(Long userId);

}
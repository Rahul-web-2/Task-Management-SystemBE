package com.TaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TaskManagementSystem.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

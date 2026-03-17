package com.TaskManagementSystem.Repository;

import com.TaskManagementSystem.Model.Task;
import com.TaskManagementSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
}

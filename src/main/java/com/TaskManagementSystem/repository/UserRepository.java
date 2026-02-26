package com.TaskManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TaskManagementSystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

package com.TaskManagementSystem.Service;

import com.TaskManagementSystem.Model.Project;
import com.TaskManagementSystem.Model.User;
import com.TaskManagementSystem.Repository.ProjectRepo;
import com.TaskManagementSystem.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepo projectRepository;
    private final UserRepo userRepository;

}
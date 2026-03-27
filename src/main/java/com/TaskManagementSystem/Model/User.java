package com.TaskManagementSystem.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonManagedReference("assigned-tasks")
    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
    private List<Task> assignedTasks;

    @JsonManagedReference("created-tasks")
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Task> createdTasks;

}

package com.TaskManagementSystem.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(nullable = false)
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "project")
    private List<User> users;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> tasks;
}
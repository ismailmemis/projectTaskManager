package com.taskmanager.infrastructure.task;

import com.taskmanager.infrastructure.project.ProjectEntity;
import com.taskmanager.infrastructure.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 4096)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private TaskStatus status = TaskStatus.OFFEN;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = true)
    private ProjectEntity project;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> users = new HashSet<>();

    public TaskEntity() {
    }

    public TaskEntity(String title, String description,
                      TaskStatus status, ProjectEntity project,
                      OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.project = project;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public enum TaskStatus {
        OFFEN, IN_BEARBEITUNG, ERLEDIGT
    }
}

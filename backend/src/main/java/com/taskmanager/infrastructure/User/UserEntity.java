package com.taskmanager.infrastructure.User;

import com.taskmanager.infrastructure.task.TaskEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToMany(mappedBy = "users")
    private Set<TaskEntity> tasks = new HashSet<>();
}

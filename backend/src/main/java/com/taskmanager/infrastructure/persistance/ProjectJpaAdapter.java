// src/main/java/com/taskmanager/infrastructure/persistance/ProjectJpaAdapter.java
package com.taskmanager.infrastructure.persistance;

import com.taskmanager.application.port.ProjectRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectJpaAdapter implements ProjectRepositoryPort {

    private final ProjectRepository jpa; // Spring Data

    public ProjectJpaAdapter(ProjectRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<ProjectEntity> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public List<ProjectEntity> findAll() {
        return jpa.findAll(); // falls ListCrudRepository, sonst in List kopieren
    }

    @Override
    public ProjectEntity save(ProjectEntity entity) {
        return jpa.save(entity);
    }
}

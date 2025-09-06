package com.taskmanager.application.port;

import com.taskmanager.infrastructure.persistance.ProjectEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryPort {
    Optional<ProjectEntity> findById(Long id);

    Optional<List<ProjectEntity>> findAll();

    ProjectEntity save(ProjectEntity entity);
}

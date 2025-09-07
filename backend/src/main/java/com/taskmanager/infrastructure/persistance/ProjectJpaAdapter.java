package com.taskmanager.infrastructure.persistance;

import com.taskmanager.application.port.ProjectRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectJpaAdapter implements ProjectRepositoryPort {

    private final ProjectRepository projectRepository; // Spring Data

    public ProjectJpaAdapter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<ProjectEntity> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Optional<List<ProjectEntity>> findAll() {
        return Optional.of(projectRepository.findAll());
    }

    @Override
    public ProjectEntity save(ProjectEntity entity) {
        return projectRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.findById(id).ifPresent(projectRepository::delete);
    }

}

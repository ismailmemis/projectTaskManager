package com.taskmanager.application.service;

import com.taskmanager.application.port.ProjectPort;
import com.taskmanager.application.port.ProjectRepositoryPort;
import com.taskmanager.infrastructure.persistance.ProjectEntity;
import com.taskmanager.infrastructure.persistance.TaskEntity;
import com.taskmanager.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements ProjectPort {

    private final ProjectRepositoryPort repo;
    private final ModelMapper mapper;

    public ProjectService(ProjectRepositoryPort repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Optional<ProjectDTO> findById(Long id) {
        return repo.findById(id).map(e -> mapper.map(e, ProjectDTO.class));
    }

    @Override
    public List<ProjectDTO> findAll() {
        if (repo.findAll().isPresent()) {
            var entities = repo.findAll().get(); // List<ProjectEntity>
            var dtos = new java.util.ArrayList<ProjectDTO>();
            for (var e : entities) {
                ProjectDTO d = new ProjectDTO();
                d.setId(e.getId() == null ? null : Math.toIntExact(e.getId())); // Long -> Integer
                d.setName(e.getName());
                d.setDescription(e.getDescription());
                d.setCreatedAt(e.getCreatedAt());
                d.setUpdatedAt(e.getUpdatedAt());
                dtos.add(d);
            }
            return dtos;
        } else {
            return List.of();
        }
    }

    @Override
    public ProjectDTO create(CreateProjectDTO dto) {
        System.out.println("drin in create");
        ProjectEntity entity = mapper.map(dto, ProjectEntity.class);
        var now = java.time.OffsetDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        var saved = repo.save(entity);
        return mapper.map(saved, ProjectDTO.class);
    }

    @Override
    public ProjectDTO update(Long id, UpdateProjectDTO update) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));
        if (update.getName() != null) entity.setName(update.getName());
        if (update.getDescription() != null) entity.setDescription(update.getDescription());
        var now = java.time.OffsetDateTime.now();
        entity.setUpdatedAt(now);
        var saved = repo.save(entity);
        return mapper.map(saved, ProjectDTO.class);
    }

    @Override
    public boolean deleteById(Long id) {
        if (repo.findById(id).isEmpty()) return false;
        repo.deleteById(id);
        return true;
    }

    @Override
    public TaskDTO createTaskInProject(Long projectId, CreateTaskDTO createTaskDTO) {
        var project = repo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));
        var savedTask = new TaskEntity();
        savedTask.setTitle(createTaskDTO.getTitle());
        savedTask.setDescription(createTaskDTO.getDescription());

        var status = createTaskDTO.getStatus() != null
                ? TaskEntity.TaskStatus.valueOf(createTaskDTO.getStatus().name())
                : TaskEntity.TaskStatus.OFFEN;

        savedTask.setStatus(status);

        savedTask.setProject(project);
        var now = java.time.OffsetDateTime.now();
        savedTask.setCreatedAt(now);
        savedTask.setUpdatedAt(now);

        project.addTask(savedTask);
        repo.save(project);

        var taskDto = new TaskDTO();
        taskDto.setTitle(savedTask.getTitle());
        taskDto.setId(savedTask.getId() == null ? null : Math.toIntExact(savedTask.getId()));
        taskDto.setTitle(savedTask.getTitle());
        taskDto.setDescription(savedTask.getDescription());
        taskDto.setStatus(TaskStatusDTO.valueOf(savedTask.getStatus().name()));
        taskDto.setProjectId(savedTask.getProject().getId() == null ? null : Math.toIntExact(savedTask.getProject().getId()));
        taskDto.setCreatedAt(savedTask.getCreatedAt());
        taskDto.setUpdatedAt(savedTask.getUpdatedAt());
        return taskDto;


    }
}
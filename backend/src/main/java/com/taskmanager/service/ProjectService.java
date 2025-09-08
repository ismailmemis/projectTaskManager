package com.taskmanager.service;

import com.taskmanager.infrastructure.project.ProjectEntity;
import com.taskmanager.infrastructure.project.ProjectRepository;
import com.taskmanager.infrastructure.task.TaskRepository;
import com.taskmanager.model.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ModelMapper mapper;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public ProjectService(ProjectRepository projectRepository, ModelMapper mapper, TaskRepository taskRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.mapper = mapper;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    public Optional<ProjectDTO> findById(Long id) {
        return projectRepository.findById(id).map(e -> {
            ProjectDTO dto = mapper.map(e, ProjectDTO.class);
            List<TaskDTO> taskDtos = e.getTasks().stream()
                    .map(taskEntity -> mapper.map(taskEntity, TaskDTO.class))
                    .collect(Collectors.toList());
            dto.setTasks(taskDtos);
            return dto;
        });
    }

    public List<ProjectDTO> findAll() {
        if (!projectRepository.findAll().isEmpty()) {
            var entities = projectRepository.findAll();
            var dtos = new ArrayList<ProjectDTO>();
            for (var e : entities) {
                ProjectDTO d = new ProjectDTO();
                d.setId(e.getId() == null ? null : Math.toIntExact(e.getId())); // Long -> Integer
                d.setName(e.getName());
                d.setDescription(e.getDescription());
                d.setCreatedAt(e.getCreatedAt());
                d.setUpdatedAt(e.getUpdatedAt());
                dtos.add(d);
            }
            log.info("find all projects {}", dtos);
            return dtos;
        } else {
            return List.of();
        }
    }

    public ProjectDTO create(CreateProjectDTO dto) {
        System.out.println("drin in create");
        ProjectEntity entity = mapper.map(dto, ProjectEntity.class);
        var now = java.time.OffsetDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        var saved = projectRepository.save(entity);
        return mapper.map(saved, ProjectDTO.class);
    }

    public ProjectDTO update(Long id, UpdateProjectDTO update) {
        var entity = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));
        if (update.getName() != null) entity.setName(update.getName());
        if (update.getDescription() != null) entity.setDescription(update.getDescription());
        var now = java.time.OffsetDateTime.now();
        entity.setUpdatedAt(now);
        var saved = projectRepository.save(entity);
        return mapper.map(saved, ProjectDTO.class);
    }

    public boolean deleteById(Long id) {
        if (projectRepository.findById(id).isEmpty()) return false;
        this.taskRepository.saveAll(
                taskService.getTasksForProjectById(id).stream().map(
                        taskEntity -> {
                            taskEntity.setProject(null);
                            return taskEntity;
                        }
                ).toList()
        );

        projectRepository.deleteById(id);
        return true;
    }

    public TaskDTO createTaskInProject(Long projectId, Long taskId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Projekt nicht gefunden: " + projectId));
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden: " + taskId));

        if (task.getProject() != null && !task.getProject().getId().equals(projectId)) {
            throw new IllegalStateException("Aufgabe ist bereits einem anderen Projekt zugeordnet.");
        }

        task.setProject(project);
        var savedTask = taskRepository.save(task);

        return mapper.map(savedTask, TaskDTO.class);

    }

    public TaskDTO assignTaskToProject(AssignTaskToProjectDTO assignTaskToProjectDTO) {
        var project = projectRepository.findById(assignTaskToProjectDTO.getProjectId().longValue()).orElse(null);
        var task = taskRepository.findById(assignTaskToProjectDTO.getTaskId().longValue()).orElse(null);

        project.addTask(task);
        projectRepository.save(project);
        return mapper.map(task, TaskDTO.class);
    }
}
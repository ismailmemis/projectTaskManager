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

    /**
     * Holt ein ProjectEntity anhand der ID und mapt es in ein ProjectDTO inklusive der zugehörigen Tasks.
     *
     * @param id
     * @return
     */
    public Optional<ProjectDTO> findById(Long id) {
        return projectRepository.findById(id).map(e -> {
            ProjectDTO dto = mapper.map(e, ProjectDTO.class);
            List<TaskDTO> taskDtos = e.getTasks().stream().map(taskEntity -> mapper.map(taskEntity, TaskDTO.class)).collect(Collectors.toList());
            dto.setTasks(taskDtos);
            return dto;
        });
    }

    /**
     * Liest alle Projekte aus, mapped sie manuell in DTOs und loggt das Ergebnis.
     *
     * @return
     */
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

    /**
     * Erstellt ein neues Projekt aus DTO, setzt systemseitig Timestamps
     *
     * @param dto
     * @return
     */
    public ProjectDTO create(CreateProjectDTO dto) {
        System.out.println("drin in create");
        ProjectEntity entity = mapper.map(dto, ProjectEntity.class);
        var now = java.time.OffsetDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        var saved = projectRepository.save(entity);
        return mapper.map(saved, ProjectDTO.class);
    }

    /**
     * Update-Logik mit defensivem Ansatz: Nur gesetzte Felder im Update-DTO werden gesetzt
     *
     * @param id
     * @param update
     * @return
     */
    public ProjectDTO update(Long id, UpdateProjectDTO update) {
        var entity = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));
        if (update.getName() != null) entity.setName(update.getName());
        if (update.getDescription() != null) entity.setDescription(update.getDescription());
        var now = java.time.OffsetDateTime.now();
        entity.setUpdatedAt(now);
        var saved = projectRepository.save(entity);
        return mapper.map(saved, ProjectDTO.class);
    }

    /**
     * Löscht ein Projekt nach Validierung der Existenz.
     * Vor Löschung werden alle Tasks des Projekts vom Projekt entfernt (project auf null gesetzt),
     * um referenzielle Integrität zu wahren und Inkonsistenzen zu vermeiden.
     * Persistiert geänderte Tasks vor finalem Löschvorgang.
     */
    public boolean deleteById(Long id) {
        if (projectRepository.findById(id).isEmpty()) return false;
        this.taskRepository.saveAll(taskService.getTasksForProjectById(id).stream().map(taskEntity -> {
            taskEntity.setProject(null);
            return taskEntity;
        }).toList());

        projectRepository.deleteById(id);
        return true;
    }

    /**
     * Weist eine Task einem Projekt zu (ausgehend von DTO mit IDs).
     *
     * @param assignTaskToProjectDTO
     * @return
     */
    public TaskDTO assignTaskToProject(AssignTaskToProjectDTO assignTaskToProjectDTO) {
        var project = projectRepository.findById(assignTaskToProjectDTO.getProjectId().longValue()).orElse(null);
        var task = taskRepository.findById(assignTaskToProjectDTO.getTaskId().longValue()).orElse(null);
        if (project != null && task != null) {
            project.addTask(task);
            projectRepository.save(project);
        }
        return mapper.map(task, TaskDTO.class);
    }
}
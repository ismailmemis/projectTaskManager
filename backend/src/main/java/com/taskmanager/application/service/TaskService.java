package com.taskmanager.application.service;

import com.taskmanager.application.port.TaskPort;
import com.taskmanager.application.port.TaskRepositoryPort;
import com.taskmanager.infrastructure.persistance.TaskEntity;
import com.taskmanager.model.CreateTaskDTO;
import com.taskmanager.model.TaskDTO;
import com.taskmanager.model.UpdateTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskService implements TaskPort {

    private final TaskRepositoryPort taskRepositoryPort;
    private final ModelMapper mapper;

    public TaskService(TaskRepositoryPort taskRepositoryPort, ModelMapper mapper) {
        this.taskRepositoryPort = taskRepositoryPort;
        this.mapper = mapper;

        // add custom mapping
        this.mapper.typeMap(TaskEntity.class, TaskDTO.class).addMappings(m -> {
            m.map(src -> src.getProject().getId(), TaskDTO::setProjectId);
        });
    }

    @Override
    public Optional<TaskDTO> findById(Long id) {
        return taskRepositoryPort.findById(id).map(e -> mapper.map(e, TaskDTO.class));
    }

    @Override
    public List<TaskDTO> findAll() {

        var taskEntities = taskRepositoryPort.findAll();

        if (taskEntities.isPresent()) {
            var entities = taskEntities.get();
            var dtos = new ArrayList<TaskDTO>();
            for (var e : entities) dtos.add(mapper.map(e, TaskDTO.class));
            log.info("find all tasks {}", dtos);
            return dtos;
        } else {
            return List.of();
        }
    }

    @Override
    public TaskDTO create(CreateTaskDTO dto) {
        return null;
    }

    @Override
    public TaskDTO update(Long id, UpdateTaskDTO dto) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Optional<List<TaskDTO>> findAllUnassignedTasks() {
        var taskEntities = taskRepositoryPort.findAll();

        if (taskEntities.isPresent()) {
            var entities = taskEntities.get();
            var dtos = entities.stream()
                    .filter(e -> e.getProject() == null)
                    .map(e -> mapper.map(e, TaskDTO.class))
                    .collect(Collectors.toList());
            log.info("find all unassigned tasks {}", dtos);
            return Optional.of(dtos);
        } else {
            return Optional.of(List.of());
        }
    }
}

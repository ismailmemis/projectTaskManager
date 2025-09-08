package com.taskmanager.service;

import com.taskmanager.infrastructure.task.TaskEntity;
import com.taskmanager.infrastructure.task.TaskRepository;
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
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper mapper;

    public TaskService(TaskRepository taskRepository, ModelMapper mapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;

        // add custom mapping
        this.mapper.typeMap(TaskEntity.class, TaskDTO.class).addMappings(m -> {
            m.map(src -> src.getProject().getId(), TaskDTO::setProjectId);
        });
    }

    public Optional<TaskDTO> findById(Long id) {
        return taskRepository.findById(id).map(e -> mapper.map(e, TaskDTO.class));
    }

    public List<TaskDTO> findAll() {

        var taskEntities = taskRepository.findAll();

        var dtos = new ArrayList<TaskDTO>();
        for (var e : taskEntities) dtos.add(mapper.map(e, TaskDTO.class));
        log.info("find all tasks {}", dtos);
        return dtos;

    }

    public TaskDTO create(CreateTaskDTO dto) {
        return null;
    }

    public TaskDTO update(Long id, UpdateTaskDTO dto) {
        return null;
    }

    public boolean deleteById(Long id) {
        return false;
    }

    public Optional<List<TaskDTO>> findAllUnassignedTasks() {
        var taskEntities = taskRepository.findAll();


        var dtos = taskEntities.stream()
                .filter(e -> e.getProject() == null)
                .map(e -> mapper.map(e, TaskDTO.class))
                .collect(Collectors.toList());
        log.info("find all unassigned tasks {}", dtos);
        return Optional.of(dtos);
    }

}

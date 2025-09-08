package com.taskmanager.service;

import com.taskmanager.infrastructure.task.TaskEntity;
import com.taskmanager.infrastructure.task.TaskRepository;
import com.taskmanager.infrastructure.user.UserEntity;
import com.taskmanager.infrastructure.user.UserRepository;
import com.taskmanager.model.CreateTaskDTO;
import com.taskmanager.model.TaskDTO;
import com.taskmanager.model.UpdateTaskDTO;
import com.taskmanager.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, ModelMapper mapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;

        // Custom Mapping für projektbezogene ID, um DTO flach zu halten
        // Im Task wird nur die relevante ID übernommen statt das komplette Projekt Entity
        this.mapper.typeMap(TaskEntity.class, TaskDTO.class).addMappings(m -> {
            m.map(src -> src.getProject().getId(), TaskDTO::setProjectId);
        });
    }

    public Optional<TaskDTO> findById(Long id) {
        return taskRepository.findById(id).map(e -> mapper.map(e, TaskDTO.class));
    }

    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream().map(task -> mapper.map(task, TaskDTO.class)).collect(Collectors.toList());

    }

    /**
     * Erstellt Task-Entity aus DTO, persistiert und mapped zurück.
     * Sicherstellung der sofortigen Persistenz vor Rückgabe.
     */
    public TaskDTO create(CreateTaskDTO dto) {
        TaskEntity taskEntity = mapper.map(dto, TaskEntity.class);
        taskRepository.save(taskEntity);
        return mapper.map(taskEntity, TaskDTO.class);
    }

    /**
     * Aktualisiert Task, validiert Existenz erzwingend via Exception.
     * Nur veränderliche Felder werden überschrieben, Enum-Mapping für Status.
     */
    public TaskDTO update(Long id, UpdateTaskDTO updateTaskDTO) {
        TaskEntity entity = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        entity.setTitle(updateTaskDTO.getTitle());
        entity.setDescription(updateTaskDTO.getDescription());
        entity.setStatus(TaskEntity.TaskStatus.valueOf(updateTaskDTO.getStatus().name()));

        return mapper.map(taskRepository.save(entity), TaskDTO.class);
    }

    /**
     * Löscht Task nach Existenzprüfung, Rückgabe boolean als Erfolgssignal.
     */
    public boolean deleteById(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Liefert alle Tasks, die keinem Projekt zugeordnet sind, in Optional gelistet.
     */
    public Optional<List<TaskDTO>> findAllUnassignedTasks() {
        return Optional.of(taskRepository.findByProjectIsNull().stream().map(task -> mapper.map(task, TaskDTO.class)).collect(Collectors.toList()));
    }

    /**
     * Entfernt User aus Task-Userliste, validiert beide Entitäten, persistiert Änderungen.
     */
    public TaskDTO removeUserFromTask(Long taskId, Long userId) {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        task.getUsers().remove(user);
        return mapper.map(taskRepository.save(task), TaskDTO.class);
    }

    /**
     * Listet alle User einer Task als DTOs.
     */
    public List<UserDTO> getUsersForTask(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        return task.getUsers().stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    /**
     * Fügt User zu Task hinzu, validiert doppelte Zuweisungen zur Vorbeugung von Inkonsistenzen.
     * Persistiert aktualisierte Task-Entity und mapped zurück.
     */
    public TaskDTO assignUserToTask(Long taskId, Long userId) {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (task.getUsers().contains(user)) {
            throw new RuntimeException("User already assigned to task");
        }

        task.getUsers().add(user);
        return mapper.map(taskRepository.save(task), TaskDTO.class);
    }

    /**
     * Hilfsmethode zur Ermittlung aller Tasks, die zu einem bestimmten Projekt gehören.
     * Liefert Entities für interne Geschäftslogik, nicht gemappt.
     */
    public List<TaskEntity> getTasksForProjectById(Long projectId) {
        return this.taskRepository.findByProject_Id(projectId);
    }

}

package com.taskmanager.infrastructure.task;

import com.taskmanager.api.TasksApi;
import com.taskmanager.model.CreateTaskDTO;
import com.taskmanager.model.TaskDTO;
import com.taskmanager.model.UpdateTaskDTO;
import com.taskmanager.model.UserDTO;
import com.taskmanager.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TaskController implements TasksApi {

    private final TaskService taskService;

    // constructor injection
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<TaskDTO> assignUserToTask(Long taskId, Long userId) {
        return ResponseEntity.ok(taskService.assignUserToTask(taskId, userId));
    }

    @Override
    public ResponseEntity<TaskDTO> createNewTask(CreateTaskDTO createTaskDTO) {
        return ResponseEntity.ok(taskService.create(createTaskDTO));
    }


    @Override
    public ResponseEntity<Void> deleteTask(Long id) {
        if (taskService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @Override
    public ResponseEntity<TaskDTO> getTaskById(Long id) {
        return ResponseEntity.of(taskService.findById(id));
    }

    @Override
    public ResponseEntity<List<TaskDTO>> getUnassignedTasks() {
        return ResponseEntity.of(taskService.findAllUnassignedTasks());
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsersForTask(Long taskId) {
        return ResponseEntity.ok(taskService.getUsersForTask(taskId));
    }

    @Override
    public ResponseEntity<TaskDTO> removeUserFromTask(Long taskId, Long userId) {
        return ResponseEntity.ok(taskService.removeUserFromTask(taskId, userId));
    }

    @Override
    public ResponseEntity<TaskDTO> updateTask(Long id, UpdateTaskDTO updateTaskDTO) {
        TaskDTO updated = taskService.update(id, updateTaskDTO);
        return ResponseEntity.ok(updated);
    }
}
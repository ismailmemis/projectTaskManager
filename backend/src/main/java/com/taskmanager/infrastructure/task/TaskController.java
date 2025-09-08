package com.taskmanager.infrastructure.task;

import com.taskmanager.api.TasksApi;
import com.taskmanager.model.TaskDTO;
import com.taskmanager.model.UpdateTaskDTO;
import com.taskmanager.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TaskController implements TasksApi {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
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
        log.debug("REST request to get all tasks");
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
    public ResponseEntity<TaskDTO> updateTask(Long id, UpdateTaskDTO updateTaskDTO) {
        TaskDTO updated = taskService.update(id, updateTaskDTO);
        return ResponseEntity.ok(updated);
    }
}

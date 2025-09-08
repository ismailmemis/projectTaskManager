package com.taskmanager.infrastructure.rest;

import com.taskmanager.api.TasksApi;
import com.taskmanager.application.port.TaskPort;
import com.taskmanager.model.TaskDTO;
import com.taskmanager.model.UpdateTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TaskController implements TasksApi {

    private final TaskPort taskPort;

    public TaskController(TaskPort taskPort) {
        this.taskPort = taskPort;
    }

    @Override
    public ResponseEntity<Void> deleteTask(Long id) {
        if (taskPort.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        log.debug("REST request to get all tasks");
        return ResponseEntity.ok(taskPort.findAll());
    }

    @Override
    public ResponseEntity<TaskDTO> getTaskById(Long id) {
        return ResponseEntity.of(taskPort.findById(id));
    }

    @Override
    public ResponseEntity<List<TaskDTO>> getUnassignedTasks() {
        return ResponseEntity.of(taskPort.findAllUnassignedTasks());
    }

    @Override
    public ResponseEntity<TaskDTO> updateTask(Long id, UpdateTaskDTO updateTaskDTO) {
        TaskDTO updated = taskPort.update(id, updateTaskDTO);
        return ResponseEntity.ok(updated);
    }
}

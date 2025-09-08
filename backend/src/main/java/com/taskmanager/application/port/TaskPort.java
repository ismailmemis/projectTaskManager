package com.taskmanager.application.port;

import com.taskmanager.model.CreateTaskDTO;
import com.taskmanager.model.TaskDTO;
import com.taskmanager.model.UpdateTaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskPort {

    Optional<TaskDTO> findById(Long id);

    List<TaskDTO> findAll();

    TaskDTO create(CreateTaskDTO dto);

    TaskDTO update(Long id, UpdateTaskDTO dto);

    boolean deleteById(Long id);

    Optional<List<TaskDTO>> findAllUnassignedTasks();
}

package com.taskmanager.application.service;

import com.taskmanager.application.port.TaskPort;
import com.taskmanager.model.CreateTaskDTO;
import com.taskmanager.model.TaskDTO;
import com.taskmanager.model.UpdateTaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements TaskPort {
    @Override
    public Optional<TaskDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<TaskDTO> findAll() {
        return List.of();
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
}

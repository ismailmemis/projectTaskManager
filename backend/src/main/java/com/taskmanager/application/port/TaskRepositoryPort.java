package com.taskmanager.application.port;

import com.taskmanager.infrastructure.persistance.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {
    Optional<TaskEntity> findById(Long id);

    Optional<List<TaskEntity>> findAll();

    TaskEntity save(TaskEntity taskDTO);

    void deleteById(Long id);

}

package com.taskmanager.infrastructure.persistance;

import com.taskmanager.application.port.TaskRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskJpaAdapter implements TaskRepositoryPort {

    private final TaskRepository taskRepository;

    public TaskJpaAdapter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<TaskEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<TaskEntity>> findAll() {
        System.out.println("TaskJpaAdapter.findAll");
        return Optional.of(taskRepository.findAll());
    }

    @Override
    public TaskEntity save(TaskEntity taskDTO) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}

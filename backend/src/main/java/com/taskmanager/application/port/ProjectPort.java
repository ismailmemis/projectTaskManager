package com.taskmanager.application.port;

import com.taskmanager.model.*;

import java.util.List;
import java.util.Optional;

public interface ProjectPort {

    Optional<ProjectDTO> findById(Long id);

    List<ProjectDTO> findAll();

    ProjectDTO create(CreateProjectDTO dto);

    ProjectDTO update(Long id, UpdateProjectDTO update);

    boolean deleteById(Long id);

    TaskDTO createTaskInProject(Long projectId, CreateTaskDTO createTaskDTO);
}
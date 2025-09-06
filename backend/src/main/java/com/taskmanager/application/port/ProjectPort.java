package com.taskmanager.application.port;

import com.taskmanager.model.CreateProjectDTO;
import com.taskmanager.model.ProjectDTO;
import com.taskmanager.model.UpdateProjectDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectPort {
    Optional<ProjectDTO> getById(Long id);

    List<ProjectDTO> list();

    ProjectDTO create(CreateProjectDTO dto);

    ProjectDTO update(Long id, UpdateProjectDTO update);
}
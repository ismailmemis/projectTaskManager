package com.taskmanager.infrastructure.project;

import com.taskmanager.api.ProjectApi;
import com.taskmanager.model.*;
import com.taskmanager.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController implements ProjectApi {

    private final ProjectService projectService;

    // constructor injection
    public ProjectController(ProjectService projectPort) {
        this.projectService = projectPort;
    }

    @Override
    public ResponseEntity<TaskDTO> assignTaskToProject(AssignTaskToProjectDTO assignTaskToProjectDTO) {
        TaskDTO task = projectService.assignTaskToProject(assignTaskToProjectDTO);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<ProjectDTO> createProject(CreateProjectDTO createProjectDTO) {
        return ResponseEntity.ok(projectService.create(createProjectDTO));
    }

    @Override
    public ResponseEntity<ProjectDTO> getProjectById(Long id) {
        return ResponseEntity.of(projectService.findById(id));
    }

    @Override
    public ResponseEntity<List<ProjectDTO>> listProjects() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @Override
    public ResponseEntity<ProjectDTO> updateProject(Long id, UpdateProjectDTO updateProjectDTO) {
        ProjectDTO updated = projectService.update(id, updateProjectDTO);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<Void> deleteProject(Long id) {
        if (projectService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
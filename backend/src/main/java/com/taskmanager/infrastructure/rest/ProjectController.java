package com.taskmanager.infrastructure.rest;

import com.taskmanager.api.ProjectApi;
import com.taskmanager.application.port.ProjectPort;
import com.taskmanager.model.CreateProjectDTO;
import com.taskmanager.model.ProjectDTO;
import com.taskmanager.model.UpdateProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController implements ProjectApi {

    private final ProjectPort port;

    public ProjectController(ProjectPort port) {
        this.port = port;
    }

    @Override
    public ResponseEntity<ProjectDTO> createProject(CreateProjectDTO createProjectDTO) {
        return ResponseEntity.ok(port.create(createProjectDTO));
    }

    @Override
    public ResponseEntity<ProjectDTO> getProjectById(Long id) {
        return ResponseEntity.of(port.getById(id));
    }

    @Override
    public ResponseEntity<List<ProjectDTO>> listProjects() {
        return ResponseEntity.ok(port.list());
    }

    @Override
    public ResponseEntity<ProjectDTO> updateProject(Long id, UpdateProjectDTO updateProjectDTO) {
        ProjectDTO updated = port.update(id, updateProjectDTO);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<Void> deleteProject(Long id) {
        if (port.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
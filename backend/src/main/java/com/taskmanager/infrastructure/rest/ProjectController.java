package com.taskmanager.infrastructure.rest;

import com.taskmanager.api.ProjectApi;
import com.taskmanager.application.port.ProjectPort;
import com.taskmanager.application.service.TaskService;
import com.taskmanager.model.CreateProjectDTO;
import com.taskmanager.model.ProjectDTO;
import com.taskmanager.model.TaskDTO;
import com.taskmanager.model.UpdateProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController implements ProjectApi {

    private final ProjectPort projectPort;
    private final TaskService taskService;

    public ProjectController(ProjectPort projectPort, TaskService taskService) {
        this.projectPort = projectPort;
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<ProjectDTO> createProject(CreateProjectDTO createProjectDTO) {
        return ResponseEntity.ok(projectPort.create(createProjectDTO));
    }

    @Override
    public ResponseEntity<ProjectDTO> getProjectById(Long id) {
        return ResponseEntity.of(projectPort.findById(id));
    }

    @Override
    public ResponseEntity<List<ProjectDTO>> listProjects() {
        return ResponseEntity.ok(projectPort.findAll());
    }

    @Override
    public ResponseEntity<ProjectDTO> updateProject(Long id, UpdateProjectDTO updateProjectDTO) {
        ProjectDTO updated = projectPort.update(id, updateProjectDTO);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<Void> deleteProject(Long id) {
        if (projectPort.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<TaskDTO> assignTaskToProject(Long projectId, Long taskId) {
        var project = projectPort.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Projekt nicht gefunden: " + projectId));
        var task = taskService.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden: " + taskId));

        if (task.getProjectId() != null && (long) task.getProjectId() != projectId) {
            throw new IllegalStateException("Aufgabe ist bereits einem anderen Projekt zugeordnet.");
        }

        task.setProjectId(project.getId());

    }
}
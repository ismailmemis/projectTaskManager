package com.taskmanager.infrastructure.rest;

import com.taskmanager.api.ProjectApi;
import com.taskmanager.application.port.ProjectRepositoryPort;
import com.taskmanager.model.ProjectDTO;
import com.taskmanager.model.UpdateProjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController implements ProjectApi {

    private final ProjectRepositoryPort projectPort;
    private final ModelMapper mapper;

    public ProjectController(ProjectRepositoryPort projectPort, ModelMapper mapper) {
        this.projectPort = projectPort;
        this.mapper = mapper;
    }


    @Override
    public ResponseEntity<ProjectDTO> createProject() {
        return null;
    }

    @Override
    public ResponseEntity<ProjectDTO> getProjectById(Long id) {
        return ResponseEntity.of(
                projectPort.findById(id).map(e -> mapper.map(e, ProjectDTO.class))
        );
    }

    @Override
    public ResponseEntity<List<ProjectDTO>> listProjects() {
        return null;
    }

    @Override
    public ResponseEntity<ProjectDTO> updateProject(Integer id, UpdateProjectDTO updateProjectDTO) {
        return null;
    }
}

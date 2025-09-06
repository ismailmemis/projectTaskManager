package com.taskmanager.infrastructure.rest;

import com.taskmanager.api.ProjectApi;
import com.taskmanager.model.Project;
import com.taskmanager.model.UpdateProject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProjectController implements ProjectApi {
    @Override
    public ResponseEntity<Object> createProject() {
        return null;
    }

    @Override
    public ResponseEntity<Project> getProjectById(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Project>> listProjects() {
        return null;
    }

    @Override
    public ResponseEntity<Project> updateProject(Integer id, UpdateProject updateProject) {
        return null;
    }
}

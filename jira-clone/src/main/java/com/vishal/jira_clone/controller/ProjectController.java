package com.vishal.jira_clone.controller;

import com.vishal.jira_clone.entity.Project;
import com.vishal.jira_clone.service.ProjectService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping("/user/{userId}")
    public Project createProject(@PathVariable Long userId, @RequestBody Project project){
        return projectService.createProject(userId, project);
    }
}

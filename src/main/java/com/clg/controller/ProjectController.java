package com.clg.controller;

import com.clg.entity.Blog;
import com.clg.model.Project;
import com.clg.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        log.info("In Create Project");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        project.setCreatedBy(username);
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.ok(createdProject);
    }
    @GetMapping("/getProjectsByName")
    public ResponseEntity<List<Project>> getProjects() {
        log.info("In get Project");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return ResponseEntity.ok(projectService.getProjects(username));
    }

    @PostMapping("/update/{projectId}/{action}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @PathVariable  String action) {
        Project updateProject = projectService.updateProject(projectId, action);
        return ResponseEntity.ok(updateProject);
    }
}

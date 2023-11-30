package com.clg.controller;

import com.clg.entity.Blog;
import com.clg.model.Project;
import com.clg.model.ProjectDTO;
import com.clg.projections.ProjectProjection;
import com.clg.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
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
    public ResponseEntity<List<Project>> getProjects(@RequestParam(value = "username", required = false) String user, @RequestParam(value = "operator", required = false) String operator) {
        log.info("In get Project");
        return ResponseEntity.ok(projectService.getProjects(user, operator));
    }

    @GetMapping("/searchProjects")
    public ResponseEntity<List<ProjectProjection>> searchProjects(@RequestParam("title") String title) {
        log.info(title);
        if (StringUtils.hasText(title)) {
            return ResponseEntity.ok(projectService.searchProjects(title));
        }
        return ResponseEntity.badRequest().body(Collections.emptyList());
    }

    @GetMapping("/viewProject/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable("projectId") Long projectId) {
        if (null == projectId) {
            return ResponseEntity.badRequest().body(null);
        }
        Project existingProject = projectService.getProjectById(projectId);

        if (null == existingProject) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(existingProject);
    }

    @PostMapping("/update/{projectId}/{action}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @PathVariable String action) {
        Project updateProject = projectService.updateProject(projectId, action);
        return ResponseEntity.ok(updateProject);
    }

    @GetMapping("/trendingProjects")
    public ResponseEntity<?> getTrendingProjects(){
        List<Project> projects = projectService.getTrendingProjects();
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/topProjects")
    public ResponseEntity<?> getTopProjects(){
        List<Project> projects = projectService.getTopProjects();
        return ResponseEntity.ok().body(projects);
    }

}

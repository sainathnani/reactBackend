package com.clg.service;

import com.clg.entity.Blog;
import com.clg.model.Profile;
import com.clg.model.Project;
import com.clg.repository.ProfileRepository;
import com.clg.repository.ProjectRepository;
import com.clg.sequence.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public Project createProject(Project project) {
        project.setProjectId(sequenceGeneratorService.generateSequence(Project.SEQUENCE_NAME));
        project.setStatus("Pending");
        return projectRepository.save(project);
    }

    public List<Project> getProjects(String username) {

        if(username.contains("admin")){
           return projectRepository.findAll();
        }

        return projectRepository.findProjectByCreatedBy(username);
    }

    public Project updateProject(Long projectId) {
        Optional<Project> existingProject = projectRepository.findById(projectId);
        Project updatedProject = existingProject.get();
        updatedProject.setStatus("Approved");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        updatedProject.setModifiedBy(username);
        updatedProject.setModifiedDate(new Date());
        projectRepository.save(updatedProject);
        return updatedProject;
    }

}



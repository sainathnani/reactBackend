package com.clg.service;

import com.clg.entity.Blog;
import com.clg.model.*;
import com.clg.projections.ProjectProjection;
import com.clg.repository.*;
import com.clg.sequence.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    DislikeRepository dislikeRepository;

    public Project createProject(Project project) {
        if(null == project.getProjectId()){
            log.info("New Project");
            project.setProjectId(sequenceGeneratorService.generateSequence(Project.SEQUENCE_NAME));
            project.setStatus("Pending");
        }
        return projectRepository.save(project);
    }

    public List<Project> getProjects(String username, String operator) {

        List<Project> existingProjects = null;
        boolean getLikedUsers = true;

        if(!StringUtils.hasText(username)){
            existingProjects =  projectRepository.findAll();
            getLikedUsers = false;
        }else if(StringUtils.hasText(username) && operator != null && operator.equalsIgnoreCase("not")) {
            existingProjects = projectRepository.findProjectByCreatedByIsNot(username);
        } else {
            existingProjects = projectRepository.findProjectByCreatedBy(username);
        }

        for(Project existingProject : existingProjects) {
            int commentsCount = commentRepository.findCommentsByProjectId(existingProject.getProjectId()).size();

            if(getLikedUsers) {

                Optional<Like> like = likeRepository.findLikeByLikedByUsernameAndProjectId(username, existingProject.getProjectId());

                existingProject.setLikedByUser(like.isPresent());

                Optional<Dislike> dislike = dislikeRepository.findDislikeByDislikedByUsernameAndProjectId(username, existingProject.getProjectId());

                existingProject.setDislikedByUser(dislike.isPresent());
            }

            existingProject.setCommentsCount(commentsCount);
        }

        return existingProjects;
    }

    public List<ProjectProjection> searchProjects(String title) {

        return projectRepository.findProjectsByTitleContainingIgnoreCaseOrCategoriesContainingIgnoreCase(title, List.of(title));
    }

    public Project getProjectById(Long projectId) {

        Optional<Project> savedProject = projectRepository.findById(projectId);
        Project existingProject = null;

        if(savedProject.isPresent()){

            int commentsCount = commentRepository.findCommentsByProjectId(projectId).size();

            existingProject = savedProject.get();
            existingProject.setCommentsCount(commentsCount);
        }

        return existingProject;
    }

    public Project updateProject(Long projectId, String action) {
        Optional<Project> existingProject = projectRepository.findById(projectId);
        Project updatedProject = existingProject.get();
        updatedProject.setStatus(action.equalsIgnoreCase("APPROVED") ? "Approved": "Rejected");
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

    private String getUserName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    public List<Project> getTrendingProjects() {

        return projectRepository.getAllByCollaboratorsListIsNotNullAndPrivacyLike("Public");

    }

    public List<Project> getTopProjects() {

        return projectRepository.getAllByLikesGreaterThan(0);

    }
}



package com.clg.service;

import com.clg.model.Profile;
import com.clg.model.Project;
import com.clg.repository.ProfileRepository;
import com.clg.repository.ProjectRepository;
import com.clg.sequence.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public Project createProject(Project project) {
        project.setProjectId(sequenceGeneratorService.generateSequence(Project.SEQUENCE_NAME));
        return projectRepository.save(project);
    }

    public List<Project> getProjects(String username) {

        return projectRepository.findProjectByCreatedBy(username);
    }

}



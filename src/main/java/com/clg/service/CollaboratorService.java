package com.clg.service;


import com.clg.dto.CollaborateDTO;
import com.clg.dto.ViewCollaboratorDTO;
import com.clg.model.Collaborators;
import com.clg.model.Profile;
import com.clg.model.Project;
import com.clg.repository.CollaboratorRepository;
import com.clg.repository.ProfileRepository;
import com.clg.repository.ProjectRepository;
import com.clg.sequence.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CollaboratorService {

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProfileRepository profileRepository;

    public Collaborators saveCollaborator(Collaborators collaboratorReq) {
        log.info("{}", collaboratorReq);

        List<Collaborators> existingCollaborators =
                collaboratorRepository.findCollaboratorsByProjectIdAndRequestedByUsernameAndRequestedForUsername(collaboratorReq.getProjectId(), collaboratorReq.getRequestedByUsername(), collaboratorReq.getRequestedForUsername());

        if (!existingCollaborators.isEmpty()) {
            return null;
        }

        collaboratorReq.setCollaboratorId(sequenceGeneratorService.generateSequence(Collaborators.SEQUENCE_NAME));
        collaboratorReq.setStatus("Pending");
        collaboratorReq.setCreatedBy(collaboratorReq.getRequestedByUsername());

        return collaboratorRepository.save(collaboratorReq);

    }

    public List<ViewCollaboratorDTO> getCollaboratorsRequestedForProject() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        List<Collaborators> collaboratorsList = collaboratorRepository.findCollaboratorsByRequestedForUsernameAndStatus(username, "Pending");

        List<ViewCollaboratorDTO> collaboratorDTOList = new ArrayList<>();
        for (Collaborators collaborator : collaboratorsList) {
            ViewCollaboratorDTO viewCollaboratorDTO = new ViewCollaboratorDTO();

            viewCollaboratorDTO.setCollaborator(collaborator);
            Optional<Project> optionalProject = projectRepository.findById(collaborator.getProjectId());

            optionalProject.ifPresent(viewCollaboratorDTO::setProject);

            Profile profile = profileRepository.findByUsername(collaborator.getRequestedByUsername());
            viewCollaboratorDTO.setProfile(profile);
            collaboratorDTOList.add(viewCollaboratorDTO);
        }


        return collaboratorDTOList;
    }

    public Integer getCollaborationCount() {

        List<Collaborators> collaboratorsList = collaboratorRepository.findCollaboratorsByRequestedForUsernameAndStatus(getContextUsername(), "Pending");

        return collaboratorsList.size();
    }

    public String updateCollaborationStatus(CollaborateDTO collaborateDTO) {

        Optional<Collaborators> optionalCollaborators = collaboratorRepository.findById(collaborateDTO.getCollaboratorId());
        Project project = projectRepository.findById(collaborateDTO.getProjectId()).get();

        if (optionalCollaborators.isPresent() && !ObjectUtils.isEmpty(project)) {
            Collaborators collaborators = optionalCollaborators.get();

            collaborators.setStatus(collaborateDTO.getStatus().equalsIgnoreCase("Approved") ? "Approved" : "Rejected");

            collaborators.setModifiedDate(new Date());
            collaborators.setModifiedBy(getContextUsername());

            collaboratorRepository.save(collaborators);

            if(collaborateDTO.getStatus().equalsIgnoreCase("Approved")){
                project.getCollaboratorsList().add(collaborateDTO.getRequestedByUserName());

                projectRepository.save(project);
                return collaborateDTO.getStatus();
            } else if(collaborateDTO.getStatus().equalsIgnoreCase("Rejected")){
                project.getCollaboratorsList().remove(collaborateDTO.getRequestedByUserName());

                projectRepository.save(project);
                return collaborateDTO.getStatus();
            }

            return collaborateDTO.getStatus();
        }
        return "Failure";
    }

    private String getContextUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}

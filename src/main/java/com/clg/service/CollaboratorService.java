package com.clg.service;


import com.clg.model.Collaborators;
import com.clg.repository.CollaboratorRepository;
import com.clg.sequence.SequenceGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CollaboratorService {

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    CollaboratorRepository collaboratorRepository;

    public Collaborators saveCollaborator(Collaborators collaboratorReq) {
        log.info("{}", collaboratorReq);

        List<Collaborators> existingCollaborators =
                collaboratorRepository.findCollaboratorsByProjectIdAndRequestedByUsernameAndRequestedForUsername(collaboratorReq.getProjectId(), collaboratorReq.getRequestedByUsername(), collaboratorReq.getRequestedForUsername());

        if (!existingCollaborators.isEmpty()) {
            return null;
        }

        collaboratorReq.setCollaboratorId(sequenceGeneratorService.generateSequence(Collaborators.SEQUENCE_NAME));
        collaboratorReq.setStatus("Pending");

        return collaboratorRepository.save(collaboratorReq);

    }
}

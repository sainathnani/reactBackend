package com.clg.controller;


import com.clg.base.ErrorMessages;
import com.clg.dto.CollaborateDTO;
import com.clg.dto.ViewCollaboratorDTO;
import com.clg.model.Collaborators;
import com.clg.service.CollaboratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/collaborators")
public class CollaboratorController {

    @Autowired
    CollaboratorService collaboratorService;

    @PostMapping("/collaborate")
    public ResponseEntity<?> collaborate(@RequestBody Collaborators collaboratorRequest) {
        Collaborators savedCol = collaboratorService.saveCollaborator(collaboratorRequest);

        if (null == savedCol) {
            ErrorMessages errorMessages1 = new ErrorMessages(HttpStatus.BAD_REQUEST.value(),"Duplicate Exists");
            return ResponseEntity.badRequest().body(errorMessages1);
        }

        return ResponseEntity.ok(savedCol);

    }

    @GetMapping("/viewCollaborators")
    public ResponseEntity<?> getCollaboratorsRequestedForProject() {

        List<ViewCollaboratorDTO> collaboratorsList = collaboratorService.getCollaboratorsRequestedForProject();

        return ResponseEntity.ok(collaboratorsList);


    }

    @GetMapping("/getCollaborationCount")
    public ResponseEntity<?> getCollaborationCount() {

        Integer count = collaboratorService.getCollaborationCount();

        return ResponseEntity.ok(count);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateCollaborationStatus(@RequestBody CollaborateDTO collaborateDTO) {

        String collaborationStatus = collaboratorService.updateCollaborationStatus(collaborateDTO);

        if(collaborationStatus.equalsIgnoreCase("Failure")) {
            ErrorMessages errorMessages1 = new ErrorMessages(HttpStatus.BAD_REQUEST.value(),"Collaboration Failed");
            return ResponseEntity.badRequest().body(errorMessages1);
        }
        else if(collaborationStatus.equalsIgnoreCase("Rejected")) {
            ErrorMessages errorMessages1 = new ErrorMessages(HttpStatus.OK.value(),"Collaboration Rejected");
            return ResponseEntity.ok().body(errorMessages1);
        }

        return ResponseEntity.ok(new ErrorMessages(HttpStatus.OK.value(),"Collaboration Approved"));


    }
}

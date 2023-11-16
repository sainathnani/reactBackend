package com.clg.controller;


import com.clg.model.Collaborators;
import com.clg.service.CollaboratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/collaborators")
public class CollaboratorController {

    @Autowired
    CollaboratorService collaboratorService;

    @PostMapping("/collaborate")
    public ResponseEntity<Collaborators> collaborate(@RequestBody Collaborators collaboratorRequest) {
            Collaborators savedCol =   collaboratorService.saveCollaborator(collaboratorRequest);

            if(null != savedCol){
                ResponseEntity.badRequest();
            }

            return ResponseEntity.ok(savedCol);

    }
}

package com.clg.controller;

import com.clg.dto.flag.FlagRequest;
import com.clg.dto.flag.FlagResponse;
import com.clg.dto.flag.FlagStatusRequest;
import com.clg.dto.flag.FlagStatusResponse;
import com.clg.model.Comments;
import com.clg.model.Flag;
import com.clg.service.CommentService;
import com.clg.service.FlagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/flag")
public class FlagController {

    @Autowired
    FlagService flagService;

    @PostMapping("/flagComment")
    public ResponseEntity<?> flagComment(@RequestBody FlagRequest flagRequest) {
        log.info("In flag Comments");
        FlagResponse response = flagService.flagComment(flagRequest);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping ("/getFlaggedUsers")
    public ResponseEntity<?> getFlaggedUsers() {
        log.info("In Get flagged users");

        return ResponseEntity.ok().body(flagService.getFlaggedUsers());
    }

    @GetMapping ("/getFlaggedUserData/{emailId}")
    public ResponseEntity<?> getFlaggedUserData(@PathVariable("emailId") String emailId) {
        log.info("In Get flagged users data");

        return ResponseEntity.ok().body(flagService.getFlaggedUserData(emailId));
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateFlagStatus(@RequestBody FlagStatusRequest flagStatusRequest) {
        log.info("In update Status flag");

        FlagStatusResponse response = flagService.updateFlagStatus(flagStatusRequest);

        return ResponseEntity.ok().body(response);


    }
}

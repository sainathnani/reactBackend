package com.clg.controller;

import com.clg.model.Comments;
import com.clg.model.Project;
import com.clg.projections.ProjectProjection;
import com.clg.service.CommentService;
import com.clg.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<Comments> addComment(@RequestBody Comments comments) {
        log.info("In Add Comments");
        Comments result = commentService.addComment(comments);
        return ResponseEntity.ok(result);
    }

    @GetMapping ("/getComments/{projectId}")
    public ResponseEntity<List<Comments>> getComments(@PathVariable ("projectId") Long projectId) {
        log.info("In Get Comments");
        List<Comments> result = commentService.getCommentsByProjectId(projectId);
        return ResponseEntity.ok(result);
    }
}

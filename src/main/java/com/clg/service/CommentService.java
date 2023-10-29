package com.clg.service;

import com.clg.model.Comments;
import com.clg.model.Project;
import com.clg.projections.ProjectProjection;
import com.clg.repository.CommentRepository;
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
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    public Comments addComment(Comments comments) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        comments.setCommentId(sequenceGeneratorService.generateSequence(Comments.SEQUENCE_NAME));
        comments.setEmailId(username);
        return commentRepository.save(comments);
    }


    public List<Comments> getCommentsByProjectId(Long projectId) {

        return commentRepository.findCommentsByProjectIdOrderByCommentedDateDesc(projectId);


    }
}



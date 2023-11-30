package com.clg.service;


import com.clg.dto.dislike.DislikeRequest;
import com.clg.dto.dislike.DislikeResponse;
import com.clg.dto.like.LikeRequest;
import com.clg.model.Dislike;
import com.clg.model.Like;
import com.clg.model.Project;
import com.clg.repository.DislikeRepository;
import com.clg.repository.LikeRepository;
import com.clg.repository.ProjectRepository;
import com.clg.sequence.SequenceGeneratorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DislikeService {

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    DislikeRepository dislikeRepository;

    @Autowired
    LikeRepository likeRepository;

    public DislikeResponse dislikeProject(DislikeRequest dislikeRequest) {

        DislikeResponse response = new DislikeResponse();

        Optional<Project> optionalProject = projectRepository.findById(dislikeRequest.getProjectId());

        if (!optionalProject.isPresent()) {
            response.setDislikeStatus("Failed to Like");
            return response;
        }
        Dislike entityDislike = new Dislike();
        Project dislikedProject = optionalProject.get();

        int dislikeCount = null != dislikedProject.getDislikes() ? dislikedProject.getDislikes() : 0;

        int likeCount = null != dislikedProject.getLikes() ? dislikedProject.getLikes() : 0;

        Optional<Dislike> dbLike = dislikeRepository.findDislikeByDislikedByUsernameAndProjectId(getContextUsername(), dislikeRequest.getProjectId());
        if (dislikeRequest.isDisliked() && dbLike.isEmpty()) {
            int totalDisLikeCountTillDate = dislikeCount + 1;

            int likeCountTillDate = likeCount - 1;

            dislikedProject.setDislikes(Math.max(totalDisLikeCountTillDate,0));
            dislikedProject.setLikes(Math.max(likeCountTillDate,0));
            projectRepository.save(dislikedProject);

            // remove any likes for same project

            Optional<Like> existingLike = likeRepository.findLikeByLikedByUsernameAndProjectId(getContextUsername(), dislikeRequest.getProjectId());

            existingLike.ifPresent(like -> likeRepository.delete(like));

            entityDislike.setDislikedByUsername(getContextUsername());
            entityDislike.setProjectId(dislikedProject.getProjectId());
            entityDislike.setDislikedByUser(true);
            entityDislike.setDislikeStatus("Disliked Project");
            entityDislike.setDislikeId(sequenceGeneratorService.generateSequence(Dislike.SEQUENCE_NAME));
            entityDislike.setTotalDislikes(Math.max(totalDisLikeCountTillDate,0));
            entityDislike = dislikeRepository.save(entityDislike);
            response.setTotalLikes(likeCountTillDate);
        } else if (!dislikeRequest.isDisliked() && dbLike.isPresent()) {
            dislikeRepository.delete(dbLike.get());
            int totalLikeCountTillDate = dislikeCount - 1;
            dislikedProject.setDislikes(Math.max(totalLikeCountTillDate,0));
            projectRepository.save(dislikedProject);
            entityDislike.setDislikeStatus("Deleted dislike");
            entityDislike.setTotalDislikes(Math.max(totalLikeCountTillDate,0));
        }

        BeanUtils.copyProperties(entityDislike, response);

        return response;
    }

    private String getContextUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}

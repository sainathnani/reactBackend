package com.clg.service;


import com.clg.dto.like.LikeRequest;
import com.clg.dto.like.LikeResponse;
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
public class LikeService {

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    DislikeRepository dislikeRepository;

    public LikeResponse likeProject(LikeRequest like) {
        Like entityLike = new Like();

        LikeResponse response = new LikeResponse();

        Optional<Project> optionalProject = projectRepository.findById(like.getProjectId());


        if (!optionalProject.isPresent()) {
            response.setLikeStatus("Failed to Like");
            return response;
        }
        Project likedProject = optionalProject.get();
        int likeCount = null != likedProject.getLikes() ? likedProject.getLikes() : 0;


        Optional<Like> dbLike = likeRepository.findLikeByLikedByUsernameAndProjectId(getContextUsername(), like.getProjectId());


        int dislikeCount = null != likedProject.getDislikes() ? likedProject.getDislikes() : 0;


        if (like.isLiked() && dbLike.isEmpty()) {
            int totalLikeCountTillDate = likeCount + 1;

            int totalDisLikeCountTillDate = dislikeCount - 1;


            likedProject.setLikes(Math.max(totalLikeCountTillDate, 0));
            likedProject.setDislikes(Math.max(totalDisLikeCountTillDate, 0));
            projectRepository.save(likedProject);

            // remove any dislikes for same project

            Optional<Dislike> dbDisLike = dislikeRepository.findDislikeByDislikedByUsernameAndProjectId(getContextUsername(), likedProject.getProjectId());

            dbDisLike.ifPresent(dislike -> dislikeRepository.delete(dbDisLike.get()));


            entityLike.setLikedByUsername(getContextUsername());
            entityLike.setProjectId(likedProject.getProjectId());
            entityLike.setLikedByUser(true);
            entityLike.setLikeStatus("Liked Project");
            entityLike.setLikeId(sequenceGeneratorService.generateSequence(Like.SEQUENCE_NAME));
            entityLike.setTotalLikes(totalLikeCountTillDate);
            entityLike = likeRepository.save(entityLike);
            response.setTotalDislikes(Math.max(totalDisLikeCountTillDate,0));
        } else if (!like.isLiked() && dbLike.isPresent()) {
            likeRepository.delete(dbLike.get());
            int totalLikeCountTillDate = likeCount - 1;
            likedProject.setLikes(Math.max(totalLikeCountTillDate,0));
            projectRepository.save(likedProject);
            entityLike.setLikeStatus("Deleted Like");
            entityLike.setTotalLikes(Math.max(totalLikeCountTillDate,0));
        }

        BeanUtils.copyProperties(entityLike, response);

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

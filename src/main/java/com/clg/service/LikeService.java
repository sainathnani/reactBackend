package com.clg.service;


import com.clg.dto.like.LikeRequest;
import com.clg.model.Like;
import com.clg.model.Project;
import com.clg.repository.LikeRepository;
import com.clg.repository.ProjectRepository;
import com.clg.sequence.SequenceGeneratorService;
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

    public Like likeProject(LikeRequest like) {
        Like entityLike = new Like();

        Optional<Project> optionalProject = projectRepository.findById(like.getProjectId());


        if (!optionalProject.isPresent()) {
            entityLike.setLikeStatus("Failed to Like");
            return entityLike;
        }
        Project likedProject = optionalProject.get();
        int likeCount = null != likedProject.getLikes() ? likedProject.getLikes() : 0;
        Optional<Like> dbLike = likeRepository.findLikeByLikedByUsernameAndProjectId(getContextUsername(), like.getProjectId());
        if (like.isLiked() && dbLike.isEmpty()) {
            int totalLikeCountTillDate = likeCount + 1;
            likedProject.setLikes(totalLikeCountTillDate);
            projectRepository.save(likedProject);
            entityLike.setLikedByUsername(getContextUsername());
            entityLike.setProjectId(likedProject.getProjectId());
            entityLike.setLikedByUser(true);
            entityLike.setLikeStatus("Liked Project");
            entityLike.setLikeId(sequenceGeneratorService.generateSequence(Like.SEQUENCE_NAME));
            entityLike.setTotalLikes(totalLikeCountTillDate);
            return likeRepository.save(entityLike);
        } else if (!like.isLiked() && dbLike.isPresent()) {
            likeRepository.delete(dbLike.get());
            int totalLikeCountTillDate = likeCount - 1;
            likedProject.setLikes(totalLikeCountTillDate);
            projectRepository.save(likedProject);
            entityLike.setLikeStatus("Deleted Like");
            entityLike.setTotalLikes(totalLikeCountTillDate);
            return entityLike;
        }
        return entityLike;
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

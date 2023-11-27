package com.clg.dto.like;

import com.clg.model.Project;
import lombok.Data;

import java.util.Date;

@Data
public class LikeResponse {
    private Long likeId;
    private String likedByUsername;
    private Long projectId;
    private Date createdDate;
    private boolean isLiked;
    private String likeStatus;
    private Integer likeCount;
    private Project project;

}

package com.clg.dto.like;

import com.clg.model.Project;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
public class LikeResponse {
    private Long likeId;
    private String likedByUsername;
    private Long projectId;
    private Date createdDate;
    private boolean likedByUser;
    private String likeStatus;
    private Integer totalLikes;
    private Integer totalDislikes;

}

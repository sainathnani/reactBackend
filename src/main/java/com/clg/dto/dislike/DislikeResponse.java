package com.clg.dto.dislike;

import com.clg.model.Project;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
public class DislikeResponse {


    private Long dislikeId;
    private String dislikedByUsername;
    private Long projectId;
    private Date createdDate;
    private boolean dislikedByUser;
    private String dislikeStatus;
    private Integer totalDislikes;
    private Integer totalLikes;

}

package com.clg.dto.dislike;


import lombok.Data;

@Data
public class DislikeRequest {

    private Long dislikeId;
    private Long projectId;
    private boolean disliked;
}

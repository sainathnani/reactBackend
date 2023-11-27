package com.clg.dto.like;


import lombok.Data;

@Data
public class LikeRequest {

    private Long likeId;
    private Long projectId;
    private boolean liked;
}

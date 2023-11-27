package com.clg.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("like")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Like {
    @Transient
    public static final String SEQUENCE_NAME = "like_sequence";
    @Id
    private Long likeId;
    @Indexed(unique = true)
    private String likedByUsername;
    private Long projectId;
    private Date createdDate = new Date();
    private boolean likedByUser;
    private String likeStatus;
    private Integer totalLikes;

}

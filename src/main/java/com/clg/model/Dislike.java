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

@Document("dislike")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dislike {
    @Transient
    public static final String SEQUENCE_NAME = "dislike_sequence";
    @Id
    private Long dislikeId;
    @Indexed(unique = true)
    private String dislikedByUsername;
    private Long projectId;
    private Date createdDate = new Date();
    private boolean dislikedByUser;
    private String dislikeStatus;
    private Integer totalDislikes;

}

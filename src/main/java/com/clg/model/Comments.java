package com.clg.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Transient
    public static final String SEQUENCE_NAME = "comment_sequence";
    @Id
    private Long commentId;
    private String comment;
    private Long projectId;
    private String emailId;
    private Date commentedDate = new Date();
}

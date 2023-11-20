package com.clg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Document("collaborators")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Collaborators {
    @Transient
    public static final String SEQUENCE_NAME = "collaborators_sequence";
    @Id
    private Long collaboratorId;
    private Long projectId; // project Id to collaborate
    private String requestedByUsername; // loggedInUser
    private String requestedForUsername; // project created user
    private Date createdDate = new Date();
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private String status;

}

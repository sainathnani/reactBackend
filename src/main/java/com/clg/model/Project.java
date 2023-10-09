package com.clg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Transient
    public static final String SEQUENCE_NAME = "project_sequence";
    @Id
    private Long projectId;
    private String title;
    private String description;
    private String contactNumber;
    private String privacy;
    private Date startDate;
    private Date endDate;
    private String technologyUsed;
    @CreatedDate
    private Date createdDate = new Date();
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Date modifiedDate;
    @LastModifiedBy
    private String modifiedBy;
    private String status = "Pending";


}

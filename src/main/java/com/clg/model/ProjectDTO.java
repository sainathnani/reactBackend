package com.clg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Long projectId;
    private String title;
    private String description;
    private String contactNumber;
    private String privacy;
    private String startDate;
    private String endDate;
    private String technologyUsed;
    private Date createdDate = new Date();
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private String status;
    private Integer likes;
    private Integer dislikes;
    private List<String> categories;
    private Integer commentsCount;
    private List<String> collaboratorsList = new ArrayList<>();
    private boolean likedByUser;
    private List<String> keywords;
    private Long projectFileId;
    private MultipartFile file;


}

package com.clg.model;

import java.util.List;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Profile {
    @Transient
    public static final String SEQUENCE_NAME = "profile_sequence";
    @Id
    private Long id;
    @Indexed(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String userimagePath;
    private String designation;
    private String address;
    private String email;
    private String mobile;
    private String skills;
    private String[] languages;
    private List<Education> education;
    private List<Project> projectHistory;
    private Long fileId;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Education {
        private String universityName;
        private String yearOfEducation;
        private String degreeType;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Project {
        private String projectName;
        private String projectDate;
        private String projectDescription;
    }
}

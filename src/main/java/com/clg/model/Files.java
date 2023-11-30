package com.clg.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Data
public class Files {

    @Transient
    public static final String SEQUENCE_NAME = "files_sequence";

    @Id
    private Long id;

    private String userName;

    private Binary file;

    private String originalFileName;

    private String contentType;

    private String fileName;

    private String fileType;

    private Long projectId;

}

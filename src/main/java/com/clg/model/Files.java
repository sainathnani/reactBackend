package com.clg.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
public class Files {
    @Id
    private String id;

    private String title;

    private Binary file;

    private String type;
}

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

@Document("flag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flag {
    @Transient
    public static final String SEQUENCE_NAME = "flag_sequence";
    @Id
    private Long flagId;
    @Indexed(unique = true)
    private String flaggedByUser; //loggedIn user
    private Long commentId;
    private Date createdDate = new Date();
    private String flaggedUser; // commented user
    private String status;

}

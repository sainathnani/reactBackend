package com.clg.dto;


import lombok.Data;

@Data
public class CollaborateDTO {

    private Long projectId;
    private String status;
    private Long collaboratorId;
    private String requestedByUserName; // will be added to collaborator list
}

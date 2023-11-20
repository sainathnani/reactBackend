package com.clg.dto;

import com.clg.model.Collaborators;
import com.clg.model.Profile;
import com.clg.model.Project;
import lombok.Data;


@Data
public class ViewCollaboratorDTO {

    private Project project;
    private Profile profile;

    private Collaborators collaborator;
}

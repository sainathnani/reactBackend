package com.clg.repository;

import com.clg.model.Collaborators;
import com.clg.model.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaboratorRepository extends MongoRepository<Collaborators, Long> {

    List<Collaborators> findCollaboratorsByProjectIdAndRequestedByUsernameAndRequestedForUsernameAndStatus
            (Long projectId, String requestedByUsername, String requestedForUsername, String status);

    List<Collaborators> findCollaboratorsByRequestedForUsernameAndStatus(String username, String status);
}





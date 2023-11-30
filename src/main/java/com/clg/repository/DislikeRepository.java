package com.clg.repository;


import com.clg.model.Dislike;
import com.clg.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DislikeRepository extends MongoRepository<Dislike, Long> {


    Optional<Dislike> findDislikeByDislikedByUsernameAndProjectId(String username, Long projectId);

}

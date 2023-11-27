package com.clg.repository;


import com.clg.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends MongoRepository<Like, Long> {
    Optional<Like> findLikeByLikedByUsernameAndProjectId(String username, Long projectId);

}

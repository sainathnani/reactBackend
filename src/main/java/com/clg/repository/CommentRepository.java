package com.clg.repository;

import com.clg.model.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comments, Long> {


    List<Comments> findCommentsByProjectIdOrderByCommentedDateDesc(Long projectId);

    List<Comments> findCommentsByProjectId(Long projectId);
}





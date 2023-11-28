package com.clg.repository;

import com.clg.model.Flag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FlagRepository extends MongoRepository<Flag, Long> {

    List<Flag> findFlagByFlaggedUserAndStatus(String emailId, String status);

    List<Flag> findFlagByStatus(String status);
}





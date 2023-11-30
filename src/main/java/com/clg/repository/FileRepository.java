package com.clg.repository;

import com.clg.model.Files;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<Files, Long> {

    Files findFilesByUserNameAndFileType(String username, String fileType);

    Files findFilesByUserNameAndProjectId(String username, Long projectId);
}


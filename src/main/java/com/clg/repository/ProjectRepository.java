package com.clg.repository;

import com.clg.model.Profile;
import com.clg.model.Project;
import com.clg.projections.ProjectProjection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, Long> {

    List<Project> findProjectByCreatedBy(String createBy);

    List<Project> findProjectByCreatedByIsNot(String createBy);

    List<ProjectProjection> findProjectsByTitleContainingIgnoreCaseOrCategoriesContainingIgnoreCase(String title, List<String> categories);

}





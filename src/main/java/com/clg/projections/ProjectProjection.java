package com.clg.projections;

import java.util.List;

public interface ProjectProjection {

    String getTitle();
    String getDescription();

    Integer getLikes();

    List<String> getCategories();
}

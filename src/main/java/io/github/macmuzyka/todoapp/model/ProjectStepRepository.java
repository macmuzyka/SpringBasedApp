package io.github.macmuzyka.todoapp.model;

import java.util.List;
import java.util.Optional;

public interface ProjectStepRepository {
    List<ProjectStep> findAll();

    Optional<ProjectStep> findById(Integer id);

    boolean existsById(Integer id);

    ProjectStep save(ProjectStep entity);

}

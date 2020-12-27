package io.github.macmuzyka.todoapp.adapter;

import io.github.macmuzyka.todoapp.model.ProjectStep;
import io.github.macmuzyka.todoapp.model.ProjectStepRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlProjectStepRepository extends ProjectStepRepository, JpaRepository<ProjectStep, Integer> {
    @Override
    @Query(nativeQuery = true, value = "SELECT COUNT(*) > 0 FROM PROJECT_STEPS WHERE id=:id")
    boolean existsById(@Param("id") Integer id);
}

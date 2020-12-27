package io.github.macmuzyka.todoapp.adapter;

import io.github.macmuzyka.todoapp.model.Project;
import io.github.macmuzyka.todoapp.model.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {
    @Override
    @Query("select distinct p from Project p join fetch p.steps") // INNER JOIN BY DEFAULT IF NOT SELECT DISTINCT AND JOIN FETCH
    List<Project> findAll();
}

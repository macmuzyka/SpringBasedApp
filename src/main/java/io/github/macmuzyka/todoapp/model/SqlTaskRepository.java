package io.github.macmuzyka.todoapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Raweshau
 * on 27.11.2020
 */

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);
}

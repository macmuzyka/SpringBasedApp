package io.github.macmuzyka.todoapp.adapter;

import io.github.macmuzyka.todoapp.model.Task;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Raweshau
 * on 27.11.2020
 */

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from TASKS where id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    @Override
    Optional<List<Task>> findAllByGroup_Id(Integer groupId);

    @Override
    Optional<List<Task>> findByDoneAndDeadlineIsBeforeOrDeadlineIsNull(
            @Param("done") boolean done,
            @Param("deadline") LocalDateTime deadline);
}

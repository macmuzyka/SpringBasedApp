package io.github.macmuzyka.todoapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();

    Page<Task> findAll(Pageable page);

    Optional<Task> findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    List<Task> findByDone(@Param("state") boolean done);

    Optional<List<Task>> findAllByGroup_Id(Integer groupId);

    Task save(Task entity);

    Optional<List<Task>> findByDoneAndDeadlineIsBeforeOrDeadlineIsNull(
            @Param("done") boolean done,
            @Param("deadline") LocalDateTime deadline);
}

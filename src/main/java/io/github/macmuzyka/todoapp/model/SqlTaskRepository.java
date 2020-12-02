package io.github.macmuzyka.todoapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Raweshau
 * on 27.11.2020
 */

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
}

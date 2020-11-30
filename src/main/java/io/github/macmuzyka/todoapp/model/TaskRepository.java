package io.github.macmuzyka.todoapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Raweshau
 * on 27.11.2020
 */

@RepositoryRestResource
interface TaskRepository extends JpaRepository<Task, Integer> {
}

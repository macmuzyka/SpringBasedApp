package io.github.macmuzyka.todoapp.controller;

import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;

/**
 * Created by Raweshau
 * on 02.12.2020
 */
@RepositoryRestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks", params = {"!sort","!page","!size"})
    ResponseEntity<?> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping(value = "/tasks", params = {"!sort","!page","!size"})
    ResponseEntity<?> readAllTasks(Pageable page) {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }
}

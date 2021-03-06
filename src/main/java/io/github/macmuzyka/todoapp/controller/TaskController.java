package io.github.macmuzyka.todoapp.controller;

import io.github.macmuzyka.todoapp.model.Task;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Raweshau
 * on 02.12.2020
 */
@RestController
@RequestMapping("/tasks")
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    public final ApplicationEventPublisher eventPublisher;
    private final TaskRepository repository;


    TaskController(final ApplicationEventPublisher eventPublisher, @Qualifier("sqlTaskRepository") final TaskRepository repository) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping(value = "/test")
    void oldFashionedWay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println(req.getParameter("foo"));
        resp.getWriter().println("test old-fashioned way");
    }

    @GetMapping(value = "/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(
                repository.findByDone(state)
        );
    }

    @GetMapping(value = "/search/undone")
    ResponseEntity<List<Task>> readUndoneTasks(@RequestParam(defaultValue = "false") boolean state) {
        return ResponseEntity.ok(
                repository.findByDone(state)
        );
    }

    @Transactional
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.updateFrom(toUpdate));
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .map(Task::toggle)
                .ifPresent(eventPublisher::publishEvent);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    ResponseEntity<Task> getTask(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/deadline")
    ResponseEntity<List<Task>> getDeadlineTasks(@RequestParam(defaultValue = "false") boolean done) {
        return repository.findByDoneAndDeadlineIsBeforeOrDeadlineIsNull(done, LocalDateTime.now())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<?> addNewTask(@RequestBody @Valid Task newTask) {
        Task taskRepositorySave = repository.save(newTask);  // new Task variable to avoid code duplication in URI.create
        return ResponseEntity.created(URI.create("/" + taskRepositorySave.getId())).body(taskRepositorySave);
    }


}

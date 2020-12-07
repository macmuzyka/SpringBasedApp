package io.github.macmuzyka.todoapp.controller;

import io.github.macmuzyka.todoapp.model.Task;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by Raweshau
 * on 02.12.2020
 */
@RestController
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks() {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> getTask(@PathVariable int id) {
        return repository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tasks")
    ResponseEntity<?> addNewTask(@RequestBody @Valid Task newTask) {
        Task taskRepositorySave = repository.save(newTask);  // new Task variable to avoid code duplication in URI.create
        return ResponseEntity.created(URI.create("/" + taskRepositorySave.getId())).body(taskRepositorySave);
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<?> deleteById(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            logger.info("Task with ID number [" + id + "] deleted");
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Task with ID number [" + id + "] NOT FOUND");
            return ResponseEntity.notFound().build();
        }
    }
}

//    // Pre 4.3 Spring mapping annotation <----------------
//    @RequestMapping(method = RequestMethod.GET, path = "/tasks/{id}")
//    ResponseEntity<Task> getTask(@PathVariable int id) {
//        return repository.findById(id)
//                .map(task -> ResponseEntity.ok(task))
//                .orElse(ResponseEntity.notFound().build());
//    }

//    @GetMapping("/tasks/{id}")
//    ResponseEntity<?> getTask(@PathVariable int id) {
//        if (repository.findById(id).isPresent()) {
//            return ResponseEntity.ok(repository.findById(id));
//        }
//        return ResponseEntity.notFound().build();
//    }
//     Pre 4.3 Spring mapping annotation <----------------
//     @RequestMapping(method = RequestMethod.GET, path = "/tasks/{id}")

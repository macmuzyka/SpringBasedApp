package io.github.macmuzyka.todoapp.controller;

import io.github.macmuzyka.todoapp.logic.TaskGroupService;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import io.github.macmuzyka.todoapp.model.projection.GroupReadModel;
import io.github.macmuzyka.todoapp.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks_groups")
public class TaskGroupController {

    private Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private TaskGroupService taskGroupService;
    private TaskRepository taskRepository;


    public TaskGroupController(final TaskGroupService taskGroupService, final TaskRepository taskRepository) {
        this.taskGroupService = taskGroupService;
        this.taskRepository = taskRepository;

    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.info("Exposing all the Task Groups!");
        return ResponseEntity.ok().body(taskGroupService.readAll());
    }

    @GetMapping("/{id}/tasks")
    ResponseEntity<?> getGivenGroupTasks(@PathVariable int id) {
        logger.info("Exposing given task!");
        return taskRepository.findAllByGroup_Id(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        logger.info("Toggling given task!");
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<GroupReadModel> addNewGroup(@RequestBody @Valid GroupWriteModel toSave) {
        logger.info("Posting new Task Group!");
        GroupReadModel newGroup = taskGroupService.createGroup(toSave);
        return ResponseEntity.created(URI.create("/" + newGroup.getId())).body(newGroup);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

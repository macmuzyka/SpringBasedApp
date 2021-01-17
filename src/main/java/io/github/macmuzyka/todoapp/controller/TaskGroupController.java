package io.github.macmuzyka.todoapp.controller;

import io.github.macmuzyka.todoapp.logic.TaskGroupService;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import io.github.macmuzyka.todoapp.model.projection.GroupReadModel;
import io.github.macmuzyka.todoapp.model.projection.GroupTaskWriteModel;
import io.github.macmuzyka.todoapp.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class TaskGroupController {

    private Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private TaskGroupService taskGroupService;
    private TaskRepository taskRepository;


    public TaskGroupController(final TaskGroupService taskGroupService, final TaskRepository taskRepository) {
        this.taskGroupService = taskGroupService;
        this.taskRepository = taskRepository;

    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        logger.info("Exposing all the Task Groups!");
        return ResponseEntity.ok().body(taskGroupService.readAll());
    }

    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> getGivenGroupTasks(@PathVariable int id) {
        logger.info("Exposing given task!");
        return taskRepository.findAllByGroup_Id(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseBody
    @Transactional
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        logger.info("Toggling given group!");
        taskGroupService.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model) {
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }

    @PostMapping(params = "addTask", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    String addTask(@ModelAttribute("group") GroupWriteModel newGroup) {
        newGroup.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }

    @PostMapping(produces = MediaType.APPLICATION_XHTML_XML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(@ModelAttribute("group") @Valid GroupWriteModel newGroup,
                    BindingResult bindingResult,
                    Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "groups";
        }
        taskGroupService.createGroup(newGroup);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Group added!");
        return "groups";
    }

    @ModelAttribute("groups")
    List<GroupReadModel> getGroups() {
        return taskGroupService.readAll();
    }
}

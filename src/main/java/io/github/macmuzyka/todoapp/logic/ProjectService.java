package io.github.macmuzyka.todoapp.logic;

import io.github.macmuzyka.todoapp.TaskConfigurationProperties;
import io.github.macmuzyka.todoapp.model.*;
import io.github.macmuzyka.todoapp.model.projection.GroupReadModel;
import io.github.macmuzyka.todoapp.model.projection.GroupTaskWriteModel;
import io.github.macmuzyka.todoapp.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final TaskGroupService taskGroupService;
    private final TaskConfigurationProperties config;


    public ProjectService(final ProjectRepository projectRepository,
                          final TaskGroupRepository taskGroupRepository,
                          final TaskGroupService taskGroupService,
                          final TaskConfigurationProperties config) {

        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = taskGroupService;
        this.config = config;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project save(Project projectToSave) {
        return projectRepository.save(projectToSave);
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {

        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one unfinished Task Group at the time!");
        }
        return projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                                var task = new GroupTaskWriteModel();
                                                task.setDescription(projectStep.getDescription());
                                                task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                                return task;
                                            }
                                    ).collect(Collectors.toSet())
                    );
                    return taskGroupService.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));

    }
}

//    Project project = projectRepository.findById(projectId)
//            .orElseThrow(() -> new IllegalArgumentException("Project with given id does not exist"));
//
//        if (taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId) && !config.getTemplate().isAllowMultipleTasks()) {
//                throw new IllegalStateException("Only one unfinished Task Group at the time!");
//                }
//
//                TaskGroup group = new TaskGroup();
//                group.setDone(false);  // new Task Group by default is not done
//                group.setDescription(project.getDescription());
//                group.setTasks(project.getSteps().stream()
//                .map(step ->
//                new Task(step.getDescription(), deadline.minusDays(step.getDaysToDeadline())))
//                .collect(Collectors.toSet()));
//                return new GroupReadModel(group);





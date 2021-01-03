package io.github.macmuzyka.todoapp.logic;

import io.github.macmuzyka.todoapp.TaskConfigurationProperties;
import io.github.macmuzyka.todoapp.model.*;
import io.github.macmuzyka.todoapp.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project save(Project projectToSave) {
        return projectRepository.save(projectToSave);
    }

    public GroupReadModel creteGroup(int projectId, LocalDateTime deadline) {

        if (taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId) && !config.getTemplate().isAllowMultipleTasks()) {
            throw new IllegalStateException("Only one unfinished Task Group at the time!");
        }
        TaskGroup result = projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(project.getSteps().stream()
                            .map(projectStep -> new Task(projectStep.getDescription(), deadline.plusDays(projectStep.getDaysToDeadline())))
                            .collect(Collectors.toSet())
                    );
                    return targetGroup;
                }).orElseThrow(() -> new IllegalStateException("Project with given id not found"));
        return new GroupReadModel(result);

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





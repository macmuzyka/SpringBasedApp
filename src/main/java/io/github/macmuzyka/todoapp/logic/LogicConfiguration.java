package io.github.macmuzyka.todoapp.logic;

import io.github.macmuzyka.todoapp.TaskConfigurationProperties;
import io.github.macmuzyka.todoapp.model.ProjectRepository;
import io.github.macmuzyka.todoapp.model.TaskGroupRepository;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {
    @Bean
    ProjectService projectService(
            final ProjectRepository repository,
            final TaskGroupRepository taskGroupRepository,
            final TaskGroupService taskGroupService,
            final TaskConfigurationProperties config
    ) {
        return new ProjectService(repository, taskGroupRepository, taskGroupService, config);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository,
            @Qualifier("sqlTaskRepository")
            final TaskRepository taskRepository // @Profile("integration") for  TaskRepository testRepo(), but qualifier added to keep IntelliJ happy

    ) {
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }
}

package io.github.macmuzyka.todoapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {
    private Template template;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}

// @Configuration annotation was required pre Spring Boot 2.2 in order for TaskConfigurationProperties to work properly
// or other annotation mentioned in TodoAppApplication comment section above @SpringBootApplication

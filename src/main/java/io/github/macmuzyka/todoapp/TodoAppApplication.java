package io.github.macmuzyka.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

// @EnableConfigurationProperties(TaskConfigurationProperties.class) this annotation was required by Spring Boot pre 2.2
// It was necessary to annotate it on the class that has @Configuration annotation (hierarchy -> @SpringBootApplication
// -> @SpringBootConfiguration -> @Configuration) in order for the class given $param in
// @EnableConfigurationProperties($param) to work / configure properly
// or annotation mentioned in TaskConfigurationProperties mentioned above @ConfigurationProperties("task") annotation

@SpringBootApplication
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}

	@Bean
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}
}

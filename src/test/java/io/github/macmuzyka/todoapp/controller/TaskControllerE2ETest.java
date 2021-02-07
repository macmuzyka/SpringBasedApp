package io.github.macmuzyka.todoapp.controller;

import io.github.macmuzyka.todoapp.model.Task;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


@Disabled("Disabled after integrating with Keycloak identity provider!")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    @Qualifier("sqlTaskRepository")
    TaskRepository repo;

    @Test
    void httpGet_returnsAllTasks() {
        // given
        int initial = repo.findAll().size(); // java V__2 migration
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));

        // when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        // then
        assertThat(result).hasSize(initial + 2);
    }

    @Test
    void httpGet_returnsGivenTask() {
        // given
        int sizeBeforeSave = repo.findAll().size();
        int taskId = sizeBeforeSave + 1;

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        repo.save(new Task("foobar", now));

        //when
        Task result = restTemplate.getForObject("http://localhost:" + port + "/tasks/" + taskId, Task.class);

        // then
        assertThat(result).hasFieldOrPropertyWithValue("description", "foobar");
        assertThat(result).hasFieldOrPropertyWithValue("deadline", now);
    }

    @Test
    void httpPost_createsNewTask() {
        // given
        int sizeBeforePost = repo.findAll().size();
        int beforePostTaskIdCheck = sizeBeforePost + 1;

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Task toPost = new Task("postTask", now);

        // when
        int postedTaskId = restTemplate.postForObject("http://localhost:" + port + "/tasks", toPost, Task.class).getId();
        Task postedTask = restTemplate.getForObject("http://localhost:" + port + "/tasks/" + beforePostTaskIdCheck, Task.class);

        // then
        assertThat(postedTaskId).isEqualTo(beforePostTaskIdCheck);
        assertThat(postedTask).hasFieldOrPropertyWithValue("description", "postTask");
        assertThat(postedTask).hasFieldOrPropertyWithValue("deadline", now);
    }
}
package io.github.macmuzyka.todoapp.controller;

import io.github.macmuzyka.todoapp.model.Task;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled("Disabled after integrating with Keycloak identity provider!")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class TaskControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    @Qualifier("sqlTaskRepository")
    private TaskRepository repo;

    @Test
    void httpGet_returnsAllTasks() throws Exception {
        // given
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));
        repo.save(new Task("foobar", LocalDateTime.now()));
        int size = repo.findAll().size(); // additional V__2 Java migration

        // when + then
        mockMvc.perform(get("/tasks"))
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(content().string(containsString("migration")))  // record from V__2 Java migration
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        // given
        repo.save(new Task("foobar", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        int taskId = repo.findAll().size();

        // when + then
        mockMvc.perform(get("/tasks/" + taskId))
                .andExpect(content().string(containsString("foobar")))
                .andExpect(status().is2xxSuccessful());
    }
}

package io.github.macmuzyka.todoapp.controller;

import io.github.macmuzyka.todoapp.model.Task;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerLightIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("sqlTaskRepository")
    private TaskRepository repo;

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        // given
        String description = "foo";
        when(repo.findById(anyInt()))
                .thenReturn(Optional.of(new Task(description, LocalDateTime.now())));

        // when + then
        mockMvc.perform(get("/tasks/256"))
                .andDo(print())
                .andExpect(content().string(containsString(description)));
    }

    @Test
    void httpGet_returnsAllTasks() throws Exception {
        // given
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));
        int size = repo.findAll().size();

        // when + then
        mockMvc.perform(get("/tasks"))
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(status().is2xxSuccessful());
    }
}

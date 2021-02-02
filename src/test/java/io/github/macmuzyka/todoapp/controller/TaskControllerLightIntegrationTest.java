package io.github.macmuzyka.todoapp.controller;

//@WebMvcTest(TaskController.class)
//public class TaskControllerLightIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    @Qualifier("sqlTaskRepository")
//    private TaskRepository repo;
//
//    @Test
//    void httpGet_returnsGivenTask() throws Exception {
//        // given
//        String description = "foo";
//        when(repo.findById(anyInt()))
//                .thenReturn(Optional.of(new Task(description, LocalDateTime.now())));
//
//        // when + then
//        mockMvc.perform(get("/tasks/256"))
//                .andDo(print())
//                .andExpect(content().string(containsString(description)));
//    }
//
//    @Test
//    void httpGet_returnsAllTasks() throws Exception {
//        // given
//        repo.save(new Task("foo", LocalDateTime.now()));
//        repo.save(new Task("bar", LocalDateTime.now()));
//        int size = repo.findAll().size();
//
//        // when + then
//        mockMvc.perform(get("/tasks"))
//                .andExpect(jsonPath("$", hasSize(size)))
//                .andExpect(status().is2xxSuccessful());
//    }
//}

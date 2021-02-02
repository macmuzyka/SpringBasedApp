package io.github.macmuzyka.todoapp.controller;

//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("integration")
//public class TaskControllerIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    @Qualifier("sqlTaskRepository")
//    private TaskRepository repo;
//
//    @Test
//    void httpGet_returnsAllTasks() throws Exception {
//        // given
//        repo.save(new Task("foo", LocalDateTime.now()));
//        repo.save(new Task("bar", LocalDateTime.now()));
//        repo.save(new Task("foobar", LocalDateTime.now()));
//        int size = repo.findAll().size(); // additional V__2 Java migration
//
//        // when + then
//        mockMvc.perform(get("/tasks"))
//                .andExpect(jsonPath("$", hasSize(size)))
//                .andExpect(content().string(containsString("migration")))  // record from V__2 Java migration
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    void httpGet_returnsGivenTask() throws Exception {
//        // given
//        repo.save(new Task("foobar", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
//        int taskId = repo.findAll().size();
//
//        // when + then
//        mockMvc.perform(get("/tasks/" + taskId))
//                .andExpect(content().string(containsString("foobar")))
//                .andExpect(status().is2xxSuccessful());
//    }
//}

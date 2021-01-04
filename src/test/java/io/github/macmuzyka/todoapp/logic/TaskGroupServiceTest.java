package io.github.macmuzyka.todoapp.logic;

import io.github.macmuzyka.todoapp.model.TaskGroup;
import io.github.macmuzyka.todoapp.model.TaskGroupRepository;
import io.github.macmuzyka.todoapp.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {
    @Test
    @DisplayName("Should throw IllegalStateException when Task Group has 1 or more undone tasks")
    void toggleGroup_existsByDoneIsFalseAndGroup_Id_True() {
        // given
        TaskRepository mockTaskRepository = getMockTaskRepository(true);
        // system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);
        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(256));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when Task Group has all tasks done and Task Group with given id does not exist")
    void toggleGroup_existsByDoneIsFalseAndGroup_Id_False_andFindById_False() {
        // given
        TaskRepository mockTaskRepository = getMockTaskRepository(false);
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(3000)).thenReturn(Optional.empty());
        // system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        // then
        var exception = catchThrowable(() -> toTest.toggleGroup(256));
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("Should be able to toggle group when Task Group has all tasks done and Task Group with given id exists")
    void toggleGroup_shouldBeAbleToToggleGroup() {
        // given
        TaskRepository mockTaskRepository = getMockTaskRepository(false);
        // and
        TaskGroup taskGroup = new TaskGroup();
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
        // and
        // should be false
        boolean beforeToggle = taskGroup.isDone();
        // system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        // then
        toTest.toggleGroup(256);
        // should be true after toggleGroup method
        boolean afterToggle = taskGroup.isDone();
        toTest.toggleGroup(256);

        assertFalse(beforeToggle);
        assertTrue(afterToggle);
    }

    private TaskRepository getMockTaskRepository(boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }
}

//    private InMemoryGroupRepository inMemoryGroupRepository() {
//        return new InMemoryGroupRepository();
//    }
//
//    private static class InMemoryGroupRepository implements TaskGroupRepository {
//        private int index = 0;
//        private Map<Integer, TaskGroup> map = new HashMap<>();
//
//        public int count() {
//            return map.values().size();
//        }
//
//        @Override
//        public List<TaskGroup> findAll() {
//            return new ArrayList<>(map.values());
//        }
//
//        @Override
//        public Optional<TaskGroup> findById(Integer id) {
//            return Optional.ofNullable(map.get(id));
//        }
//
//        @Override
//        public TaskGroup save(TaskGroup entity) {
//            if (entity.getId() == 0) {
//                try {
//                    var field = TaskGroup.class.getSuperclass().getDeclaredField("id");
//                    field.setAccessible(true);
//                    field.set(entity, ++index);
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            map.put(entity.getId(), entity);
//            return entity;
//        }
//
//        @Override
//        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
//            return map.values().stream()
//                    .filter(group -> !group.isDone())
//                    .anyMatch(group -> group.getProject() != null && group.getProject().getId() == projectId);
//        }
//    }

//    @Test
//    @DisplayName("should throw when undone tasks")
//    void toggleGroup_undoneTasks_throwsIllegalStateException() {
//        //given
//        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
//        // system under test
//        var toTest = new TaskGroupService(null, mockTaskRepository);
//        // then
//        var exception = catchThrowable(() -> toTest.toggleGroup(256));
//        assertThat(exception)
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("undone tasks");
//    }
//
//
//
//    @Test
//    @DisplayName("should throw when no group")
//    void toggleGroup_wrongId_throwsIllegalArgumentException() {
//        // given
//        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
//        // and
//        var mockRepository = mock(TaskGroupRepository.class);
//        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
//        // system under test
//        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);
//
//        // when
//        var exception = catchThrowable(() -> toTest.toggleGroup(256));
//        assertThat(exception)
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("id not found");
//
//    }
//
//    @Test
//    @DisplayName("should throw when no group")
//    void toggleGroup_shouldToggleGroup() {
//        // given
//        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
//        // and
//        var group = new TaskGroup();
//        var beforeToggle = group.isDone();
//        var mockRepository = mock(TaskGroupRepository.class);
//        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));
//        // system under test
//        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);
//
//        // when
//       toTest.toggleGroup(0);
//
//       // then
//       assertThat(group.isDone()).isEqualTo(!beforeToggle);
//
//    }
//
//    private TaskRepository taskRepositoryReturning(boolean result) {
//        var mockTaskRepository = mock(TaskRepository.class);
//        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
//        return mockTaskRepository;
//    }



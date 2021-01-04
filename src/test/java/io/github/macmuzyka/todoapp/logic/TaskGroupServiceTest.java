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



package io.github.macmuzyka.todoapp.model.event;

import io.github.macmuzyka.todoapp.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent {
    TaskUndone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}

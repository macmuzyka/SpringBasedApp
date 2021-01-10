package io.github.macmuzyka.todoapp.model.projection;

import io.github.macmuzyka.todoapp.model.Task;
import io.github.macmuzyka.todoapp.model.TaskGroup;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {

    private int id;
    private String description;
    /**
     * Deadline from the latest task in group.
     */
    private LocalDateTime deadline;
    private Set<GroupTaskReadModel> tasks;
    boolean done;



    public GroupReadModel(TaskGroup source) {
        id = source.getId();
        description = source.getDescription();
        source.getTasks().stream()
                .map(Task::getDeadline)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> deadline = date);
        tasks = source.getTasks().stream()
                .map(GroupTaskReadModel::new)
                .collect(Collectors.toSet());
        done = source.isDone();
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<GroupTaskReadModel> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTaskReadModel> tasks) {
        this.tasks = tasks;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

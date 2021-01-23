package io.github.macmuzyka.todoapp.model;

import io.github.macmuzyka.todoapp.model.event.TaskEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
public class BaseTask extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Description field MUST NOT be empty!")
    private String description;
    private boolean done;

    BaseTask() {
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public TaskEvent toggle() {
        this.done = !this.done;
        return TaskEvent.changed((Task) this);
    }


    public void updateFrom(final BaseTask source) {
        description = source.description;
        done = source.done;
    }
}

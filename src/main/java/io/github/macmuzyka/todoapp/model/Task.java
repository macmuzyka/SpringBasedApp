package io.github.macmuzyka.todoapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Raweshau
 * on 27.11.2020
 */

@Entity
@Table(name = "tasks")
public class Task extends BaseTask {

    private LocalDateTime deadline;
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;

    public Task() {

    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    TaskGroup getGroup() {
        return group;
    }

    void setGroup(TaskGroup group) {
        this.group = group;
    }
}



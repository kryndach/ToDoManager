package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 03/05/16.
 */
public class TaskUpdatedEvent {
    public final Task task;

    public TaskUpdatedEvent(Task task) {
        this.task = task;
    }
}

package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 03/05/16.
 */
public class TaskPuttedEvent {
    public final Task task;

    public TaskPuttedEvent(Task task) {
        this.task = task;
    }
}

package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 04/05/16.
 */
public class TaskInsertedEvent {
    public final Task task;

    public TaskInsertedEvent(Task task) {
        this.task = task;
    }
}

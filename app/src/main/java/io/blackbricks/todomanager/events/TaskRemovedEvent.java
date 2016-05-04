package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 03/05/16.
 */
public class TaskRemovedEvent {
    public final Integer taskId;

    public TaskRemovedEvent(Integer taskId) {
        this.taskId = taskId;
    }
}

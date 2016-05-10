package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Filter;

/**
 * Created by yegorkryndach on 06/05/16.
 */
public class TaskListEnterEvent {
    public final Filter.Type type;
    public final Integer groupId;

    public TaskListEnterEvent(Filter.Type type, Integer groupId) {
        this.type = type;
        this.groupId = groupId;
    }
}

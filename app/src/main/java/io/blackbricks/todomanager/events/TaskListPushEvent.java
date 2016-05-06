package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Filter;

/**
 * Created by yegorkryndach on 06/05/16.
 */
public class TaskListPushEvent {
    public final Filter.Type type;
    public final Integer groupId;
    public final String title;

    public TaskListPushEvent(Filter.Type type, Integer groupId, String title) {
        this.type = type;
        this.groupId = groupId;
        this.title = title;
    }
}

package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 03/05/16.
 */
public class GroupUpdatedEvent {
    public final Group group;

    public GroupUpdatedEvent(Group group) {
        this.group = group;
    }
}

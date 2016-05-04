package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 04/05/16.
 */
public class GroupInsertedEvent {
    public final Group group;

    public GroupInsertedEvent(Group group) {
        this.group = group;
    }
}

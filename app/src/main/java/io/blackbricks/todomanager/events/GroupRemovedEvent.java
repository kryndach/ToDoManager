package io.blackbricks.todomanager.events;

/**
 * Created by yegorkryndach on 03/05/16.
 */
public class GroupRemovedEvent {
    public final Integer groupId;

    public GroupRemovedEvent(Integer groupId) {
        this.groupId = groupId;
    }
}

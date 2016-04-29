package io.blackbricks.todomanager.events;

import io.blackbricks.todomanager.model.Filter;

/**
 * Created by yegorkryndach on 25/04/16.
 */
public class MenuFilterClickedEvent {
    public final Filter filter;
    public final String groupId;
    public final String title;

    public MenuFilterClickedEvent(Filter filter, String groupId, String title) {
        this.filter = filter;
        this.groupId = groupId;
        this.title = title;
    }
}

package io.blackbricks.todomanager.event;

import io.blackbricks.todomanager.model.Filter;

/**
 * Created by yegorkryndach on 25/04/16.
 */
public class MenuFilterClickedEvent {
    Filter filter;
    String groupId;
    String title;

    public MenuFilterClickedEvent(Filter filter, String groupId, String title) {
        this.filter = filter;
        this.groupId = groupId;
        this.title = title;
    }

    public Filter getFilter() {
        return filter;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getTitle() {
        return title;
    }
}

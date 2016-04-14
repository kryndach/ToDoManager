package io.blackbricks.todomanager.model;

import java.util.Date;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class Task {
    Integer id;
    Date dateAlarm;
    Date dateCreated;
    Date dateDeadline;
    Date dateStatusUpdated;
    String description;
    Integer iconId;
    TaskStatus status;
    String title;
    Integer groupId;

    public enum TaskStatus{
        UNDONE(0), DONE(1), DELAYED(2), HOT(3), OVERDUE(4);

        private final int value;
        TaskStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Integer getId() {
        return id;
    }

    public Date getDateAlarm() {
        return dateAlarm;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public Date getDateStatusUpdated() {
        return dateStatusUpdated;
    }

    public String getDescription() {
        return description;
    }

    public Integer getIconId() {
        return iconId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public Integer getGroupId() {
        return groupId;
    }
}

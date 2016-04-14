package io.blackbricks.todomanager.model;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class Attachment {
    Integer id;
    String path;
    Integer taskId;

    public Integer getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Integer getTaskId() {
        return taskId;
    }
}

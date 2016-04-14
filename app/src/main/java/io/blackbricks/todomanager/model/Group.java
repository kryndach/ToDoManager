package io.blackbricks.todomanager.model;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class Group {
    Integer id;
    String name;
    Integer taskCount;
    Integer hotTaskCount;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public Integer getHotTaskCount() {
        return hotTaskCount;
    }
}

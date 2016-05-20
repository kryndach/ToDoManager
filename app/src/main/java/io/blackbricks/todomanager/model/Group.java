package io.blackbricks.todomanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import io.blackbricks.todomanager.database.DatabaseHelper;

/**
 * Created by yegorkryndach on 14/04/16.
 */
@StorIOSQLiteType(table = "groups")
@ParcelablePlease
public class Group implements Parcelable {

    @StorIOSQLiteColumn(name = DatabaseHelper.ID_COLUMN, key = true)
    Integer id;

    @StorIOSQLiteColumn(name = DatabaseHelper.GROUP_NAME_COLUMN)
    String name;

    @StorIOSQLiteColumn(name = DatabaseHelper.GROUP_ORDER)
    Integer order;

    @StorIOSQLiteColumn(name = DatabaseHelper.GROUP_TASK_COUNT_COLUMN)
    Integer taskCount;

    @StorIOSQLiteColumn(name = DatabaseHelper.GROUP_HOT_TASK_COUNT_COLUMN)
    Integer hotTaskCount;

    Group() {
    }

    private Group(Builder builder) {
        id = builder.id;
        name = builder.name;
        order = builder.order;
        taskCount = builder.taskCount;
        hotTaskCount = builder.hotTaskCount;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public Integer getHotTaskCount() {
        return hotTaskCount;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescription() {
        String description = "Tasks " + this.getTaskCount();
        if (this.getHotTaskCount() > 0) {
            description = "Hot " + this.getHotTaskCount() + ", " + description;
        }

        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        GroupParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        public Group createFromParcel(Parcel source) {
            Group target = new Group();
            GroupParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public static final class Builder {
        private Integer id;
        private String name;
        private Integer order;
        private Integer taskCount;
        private Integer hotTaskCount;

        public Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder order(Integer val) {
            order = val;
            return this;
        }

        public Builder taskCount(Integer val) {
            taskCount = val;
            return this;
        }

        public Builder hotTaskCount(Integer val) {
            hotTaskCount = val;
            return this;
        }

        public Group build() {
            return new Group(this);
        }
    }
}

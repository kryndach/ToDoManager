package io.blackbricks.todomanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 14/04/16.
 */
@ParcelablePlease
public class Group implements Parcelable {
    Integer id;
    String name;
    Integer taskCount;
    Integer hotTaskCount;

    private Group() {
    }

    public Group(Integer id, String name, Integer taskCount, Integer hotTaskCount) {
        this.id = id;
        this.name = name;
        this.taskCount = taskCount;
        this.hotTaskCount = hotTaskCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getHotTaskCount() {
        return hotTaskCount;
    }

    public void setHotTaskCount(Integer hotTaskCount) {
        this.hotTaskCount = hotTaskCount;
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
}

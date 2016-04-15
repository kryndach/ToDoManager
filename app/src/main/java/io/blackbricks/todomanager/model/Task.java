package io.blackbricks.todomanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Date;

/**
 * Created by yegorkryndach on 14/04/16.
 */
@ParcelablePlease
public class Task implements Parcelable {

    public enum Status {
        UNDONE(0), DONE(1), DELAYED(2), HOT(3), OVERDUE(4);

        private final int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    Integer id;
    Date dateAlarm;
    Date dateCreated;
    Date dateDeadline;
    Date dateStatusUpdated;
    String description;
    Integer iconId;
    Status status;
    String title;
    Integer groupId;

    public Task() {

    }

    public Task(Integer id, Date dateAlarm, Date dateCreated, Date dateDeadline,
                Date dateStatusUpdated, String description, Integer iconId, Status status,
                String title, Integer groupId) {
        this.id = id;
        this.dateAlarm = dateAlarm;
        this.dateCreated = dateCreated;
        this.dateDeadline = dateDeadline;
        this.dateStatusUpdated = dateStatusUpdated;
        this.description = description;
        this.iconId = iconId;
        this.status = status;
        this.title = title;
        this.groupId = groupId;
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

    public Status getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public Integer getGroupId() {
        return groupId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TaskParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        public Task createFromParcel(Parcel source) {
            Task target = new Task();
            TaskParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}

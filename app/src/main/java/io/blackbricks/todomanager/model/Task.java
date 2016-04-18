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

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateAlarm() {
        return dateAlarm;
    }

    public void setDateAlarm(Date dateAlarm) {
        this.dateAlarm = dateAlarm;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public Date getDateStatusUpdated() {
        return dateStatusUpdated;
    }

    public void setDateStatusUpdated(Date dateStatusUpdated) {
        this.dateStatusUpdated = dateStatusUpdated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

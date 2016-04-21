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

    private Integer id;
    private Date dateAlarm;
    private Date dateCreated;
    private Date dateDeadline;
    private Date dateStatusUpdated;
    private String description;
    private Integer iconId;
    private Status status;
    private String title;
    private Integer groupId;

    private Task() {
    }

    private Task(Builder builder) {
        id = builder.id;
        dateAlarm = builder.dateAlarm;
        dateCreated = builder.dateCreated;
        dateDeadline = builder.dateDeadline;
        dateStatusUpdated = builder.dateStatusUpdated;
        description = builder.description;
        iconId = builder.iconId;
        status = builder.status;
        title = builder.title;
        groupId = builder.groupId;
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

    public static final class Builder {
        private Integer id;
        private Date dateAlarm;
        private Date dateCreated;
        private Date dateDeadline;
        private Date dateStatusUpdated;
        private String description;
        private Integer iconId;
        private Status status;
        private String title;
        private Integer groupId;

        public Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder dateAlarm(Date val) {
            dateAlarm = val;
            return this;
        }

        public Builder dateCreated(Date val) {
            dateCreated = val;
            return this;
        }

        public Builder dateDeadline(Date val) {
            dateDeadline = val;
            return this;
        }

        public Builder dateStatusUpdated(Date val) {
            dateStatusUpdated = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder iconId(Integer val) {
            iconId = val;
            return this;
        }

        public Builder status(Status val) {
            status = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder groupId(Integer val) {
            groupId = val;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}

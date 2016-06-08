package io.blackbricks.todomanager.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.databinding.library.baseAdapters.BR;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;

import io.blackbricks.todomanager.database.DatabaseHelper;

/**
 * Created by yegorkryndach on 14/04/16.
 */

@StorIOSQLiteType(table = "tasks")
@ParcelablePlease
public class Task extends BaseObservable implements Parcelable {

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

    @StorIOSQLiteColumn(name = DatabaseHelper.ID_COLUMN, key = true)
    Integer id;

    @Bindable
    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_DATE_ALARM_COLUMN)
    Long dateAlarm;

    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_DATE_CREATED_COLUMN)
    Long dateCreated;

    @Bindable
    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_DATE_DEADLINE_COLUMN)
    Long dateDeadline;

    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_DATE_STATUS_UPDATED_COLUMN)
    Long dateStatusUpdated;

    @Bindable
    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_DESCRIPTION_COLUMN)
    String description;

    @Bindable
    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_ICON_ID_COLUMN)
    Integer iconId;

    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_STATUS_COLUMN)
    Integer status;

    @Bindable
    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_TITLE_COLUMN)
    String title;

    @StorIOSQLiteColumn(name = DatabaseHelper.TASK_GROUP_ID_COLUMN)
    Integer groupId;

    Task() {
    }

    private Task(Builder builder) {
        setId(builder.id);
        dateAlarm = builder.dateAlarm;
        dateCreated = builder.dateCreated;
        dateDeadline = builder.dateDeadline;
        dateStatusUpdated = builder.dateStatusUpdated;
        setDescription(builder.description);
        setIconId(builder.iconId);
        status = builder.status;
        setTitle(builder.title);
        setGroupId(builder.groupId);
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getIconId() {
        return iconId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Date getDateAlarm() {
        return dateAlarm == null ? null : new Date(dateAlarm);
    }

    public Date getDateCreated() {
        return dateCreated == null ? null : new Date(dateCreated);
    }

    public Date getDateDeadline() {
        return dateDeadline == null ? null : new Date(dateDeadline);
    }

    public Date getDateStatusUpdated() {
        return dateStatusUpdated == null ? null : new Date(dateStatusUpdated);
    }

    public Status getStatus() {
        return Status.values()[status];
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
        notifyPropertyChanged(BR.iconId);
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
        notifyPropertyChanged(BR.group);
    }

    public void setDateAlarm(Date dateAlarm) {
        this.dateAlarm = dateAlarm == null ? null : dateAlarm.getTime();
        notifyPropertyChanged(BR.dateAlarm);
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated == null ? null : dateCreated.getTime();
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline == null ? null : dateDeadline.getTime();
        notifyPropertyChanged(BR.dateDeadline);
    }

    public void setDateStatusUpdated(Date dateStatusUpdated) {
        this.dateStatusUpdated = dateStatusUpdated == null ? null : dateStatusUpdated.getTime();
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public static final class Builder {
        private Integer id;
        private Long dateAlarm;
        private Long dateCreated;
        private Long dateDeadline;
        private Long dateStatusUpdated;
        private String description;
        private Integer iconId;
        private Integer status;
        private String title;
        private Integer groupId;

        public Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder dateAlarm(Date val) {
            dateAlarm = val.getTime();
            return this;
        }

        public Builder dateCreated(Date val) {
            dateCreated = val.getTime();
            return this;
        }

        public Builder dateDeadline(Date val) {
            dateDeadline = val.getTime();
            return this;
        }

        public Builder dateStatusUpdated(Date val) {
            dateStatusUpdated = val.getTime();
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
            status = val.getValue();
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

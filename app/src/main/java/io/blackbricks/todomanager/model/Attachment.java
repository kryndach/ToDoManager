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

@StorIOSQLiteType(table = "attachments")
@ParcelablePlease
public class Attachment implements Parcelable {

    public static final String image_path = "images";
    public static final String image_extension = ".jpg";

    @StorIOSQLiteColumn(name = DatabaseHelper.ID_COLUMN, key = true)
    Integer id;

    @StorIOSQLiteColumn(name = DatabaseHelper.ATTACHMENT_FILE_PATH_COLUMN)
    String path;

    @StorIOSQLiteColumn(name = DatabaseHelper.ATTACHMENT_TASK_ID_COLUMN)
    Integer taskId;

    Attachment() {
    }

    private Attachment(Builder builder) {
        id = builder.id;
        path = builder.path;
        taskId = builder.taskId;
    }

    public Integer getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        AttachmentParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        public Attachment createFromParcel(Parcel source) {
            Attachment target = new Attachment();
            AttachmentParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };

    public static final class Builder {
        private Integer id;
        private String path;
        private Integer taskId;

        public Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder path(String val) {
            path = val;
            return this;
        }

        public Builder taskId(Integer val) {
            taskId = val;
            return this;
        }

        public Attachment build() {
            return new Attachment(this);
        }
    }
}

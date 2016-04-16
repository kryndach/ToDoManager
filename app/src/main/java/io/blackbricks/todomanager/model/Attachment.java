package io.blackbricks.todomanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 14/04/16.
 */
@ParcelablePlease
public class Attachment implements Parcelable {
    Integer id;
    String path;
    Integer taskId;

    private Attachment() {
    }

    public Attachment(Integer id, String path, Integer taskId) {
        this.id = id;
        this.path = path;
        this.taskId = taskId;
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
}

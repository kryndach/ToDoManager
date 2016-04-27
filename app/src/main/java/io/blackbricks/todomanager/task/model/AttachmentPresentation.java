package io.blackbricks.todomanager.task.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import io.blackbricks.todomanager.model.Attachment;

/**
 * Created by yegorkryndach on 27/04/16.
 */
@ParcelablePlease
public class AttachmentPresentation implements Parcelable {
    Attachment attachment;
    Bitmap bitmap;

    private AttachmentPresentation() {
    }

    private AttachmentPresentation(Builder builder) {
        attachment = builder.attachment;
        bitmap = builder.bitmap;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        AttachmentPresentationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<AttachmentPresentation> CREATOR = new Creator<AttachmentPresentation>() {
        public AttachmentPresentation createFromParcel(Parcel source) {
            AttachmentPresentation target = new AttachmentPresentation();
            AttachmentPresentationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public AttachmentPresentation[] newArray(int size) {
            return new AttachmentPresentation[size];
        }
    };

    public static final class Builder {
        private Attachment attachment;
        private Bitmap bitmap;

        public Builder() {
        }

        public Builder attachment(Attachment val) {
            attachment = val;
            return this;
        }

        public Builder bitmap(Bitmap val) {
            bitmap = val;
            return this;
        }

        public AttachmentPresentation build() {
            return new AttachmentPresentation(this);
        }
    }
}

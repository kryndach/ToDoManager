package io.blackbricks.todomanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 14/04/16.
 */
@ParcelablePlease
public class Filter implements Parcelable {

    public enum Type {
        INBOX, TODAY, TOMORROW, WEEK, HOT, DONE, OVERDUE, GROUP
    }

    Type type;
    int iconRes;
    Group group;

    public Filter() {
    }

    public Filter(Type type, int iconRes, Group group) {
        this.type = type;
        this.iconRes = iconRes;
        this.group = group;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        FilterParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        public Filter createFromParcel(Parcel source) {
            Filter target = new Filter();
            FilterParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };
}

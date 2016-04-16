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
        INBOX, TODAY, TOMORROW, WEEK, HOT, DONE, OVERDUE
    }

    Type type;

    private Filter() {
    }

    public Filter(Type type) {
        this.type = type;
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

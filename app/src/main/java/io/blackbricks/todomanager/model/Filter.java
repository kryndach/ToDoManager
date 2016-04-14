package io.blackbricks.todomanager.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class Filter implements Parcelable {

    public enum Type {
        INBOX, TODAY, TOMORROW, WEEK, HOT, DONE, OVERDUE, GROUP
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }




}

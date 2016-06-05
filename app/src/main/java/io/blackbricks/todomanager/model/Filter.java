package io.blackbricks.todomanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;

/**
 * Created by yegorkryndach on 14/04/16.
 */
@ParcelablePlease
public class Filter implements Parcelable {

    public enum Type {
        INBOX(ToDoManagerApp.getContext().getString(R.string.inbox)),
        TODAY(ToDoManagerApp.getContext().getString(R.string.today)),
        TOMORROW(ToDoManagerApp.getContext().getString(R.string.tomorrow)),
        WEEK(ToDoManagerApp.getContext().getString(R.string.week)),
        HOT(ToDoManagerApp.getContext().getString(R.string.hot)),
        DONE(ToDoManagerApp.getContext().getString(R.string.done)),
        OVERDUE(ToDoManagerApp.getContext().getString(R.string.overdue)),
        GROUP(ToDoManagerApp.getContext().getString(R.string.group)),
        ALL(ToDoManagerApp.getContext().getString(R.string.all));

        private final String text;

        Type(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    Type type;

    private Filter() {
    }

    public Filter(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
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

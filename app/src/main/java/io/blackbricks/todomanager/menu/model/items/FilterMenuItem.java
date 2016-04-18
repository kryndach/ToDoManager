package io.blackbricks.todomanager.menu.model.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import io.blackbricks.todomanager.model.Filter;

/**
 * Created by yegorkryndach on 17/04/16.
 */
@ParcelablePlease
public class FilterMenuItem extends MenuItem implements Parcelable {

    Filter filter;

    private FilterMenuItem() {
    }

    public FilterMenuItem(int iconRes, String title, String description, Filter filter) {
        super(iconRes, title, description);
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        FilterMenuItemParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<FilterMenuItem> CREATOR = new Creator<FilterMenuItem>() {
        public FilterMenuItem createFromParcel(Parcel source) {
            FilterMenuItem target = new FilterMenuItem();
            FilterMenuItemParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public FilterMenuItem[] newArray(int size) {
            return new FilterMenuItem[size];
        }
    };
}

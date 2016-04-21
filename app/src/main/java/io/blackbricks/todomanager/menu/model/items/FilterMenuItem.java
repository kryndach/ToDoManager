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

    private FilterMenuItem(Builder builder) {
        iconRes = builder.iconRes;
        title = builder.title;
        description = builder.description;
        filter = builder.filter;
    }

    public Filter getFilter() {
        return filter;
    }

    public static final class Builder {
        private int iconRes;
        private String title;
        private String description;
        private Filter filter;

        public Builder() {
        }

        public Builder iconRes(int val) {
            iconRes = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder filter(Filter val) {
            filter = val;
            return this;
        }

        public FilterMenuItem build() {
            return new FilterMenuItem(this);
        }
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

package io.blackbricks.todomanager.menu.model.items;

import android.os.Parcelable;

/**
 * Created by yegorkryndach on 17/04/16.
 */
public abstract class MenuItem implements Parcelable {
    int iconRes;
    String title;
    String description;

    protected MenuItem() {
    }

    public int getIconRes() {
        return iconRes;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

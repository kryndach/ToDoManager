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

    protected MenuItem(int iconRes, String title, String description) {
        this.iconRes = iconRes;
        this.title = title;
        this.description = description;
    }
}

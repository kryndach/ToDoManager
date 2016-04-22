package io.blackbricks.todomanager.database.transforms;

import android.database.Cursor;

import io.blackbricks.todomanager.menu.model.items.GroupMenuItem;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 22/04/16.
 */
public class CursorToGroupMenuItem implements Func1<Cursor, GroupMenuItem> {
    @Override
    public GroupMenuItem call(Cursor cursor) {
        return null;
    }
}

package io.blackbricks.todomanager.database.transforms;


import android.database.Cursor;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.utils.CursorWrap;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 01/04/16.
 */
public class CursorToGroup implements Func1<Cursor, Group> {
    @Override
    public Group call(Cursor cursor) {
        CursorWrap cursorWrap = new CursorWrap(cursor);

        Integer id = cursorWrap.getInteger(DatabaseHelper.ID_COLUMN);
        String name = cursorWrap.getString(DatabaseHelper.GROUP_NAME_COLUMN);
        Integer taskCount = cursorWrap.getInteger(DatabaseHelper.GROUP_TASK_COUNT_COLUMN);
        Integer hotTaskCount = cursorWrap.getInteger(DatabaseHelper.GROUP_HOT_TASK_COUNT_COLUMN);

        return new Group(id, name, taskCount, hotTaskCount);
    }
}

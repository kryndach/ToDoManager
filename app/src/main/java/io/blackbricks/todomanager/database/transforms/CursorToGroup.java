package io.blackbricks.todomanager.database.transforms;


import android.database.Cursor;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.model.Group;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 01/04/16.
 */
public class CursorToGroup implements Func1<Cursor, Group> {
    @Override
    public Group call(Cursor cursor) {
        Integer id;
        String name;
        Integer taskCount;
        Integer hotTaskCount;

        int groupId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID_COLUMN));
        String groupTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.GROUP_NAME_COLUMN));
        int taskCount = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.GROUP_TASK_COUNT_COLUMN));
        int hotTaskCount = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.GROUP_HOT_TASK_COUNT_COLUMN));

        Group group = new Group();
        group
        return new Group();
    }
}

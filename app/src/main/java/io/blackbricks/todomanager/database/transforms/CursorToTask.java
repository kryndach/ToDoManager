package io.blackbricks.todomanager.database.transforms;


import android.database.Cursor;

import java.util.Date;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.utils.CursorWrap;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 28/03/16.
 */
public class CursorToTask implements Func1<Cursor, Task> {
    @Override
    public Task call(Cursor cursor) {
        CursorWrap cursorWrap = new CursorWrap(cursor);

        Integer id = cursorWrap.getInteger(DatabaseHelper.ID_COLUMN);
        Date dateAlarm = cursorWrap.getDate(DatabaseHelper.TASK_DATE_ALARM_COLUMN);
        Date dateCreated = cursorWrap.getDate(DatabaseHelper.TASK_DATE_CREATED_COLUMN);
        Date dateDeadline = cursorWrap.getDate(DatabaseHelper.TASK_DATE_DEADLINE_COLUMN);
        Date dateStatusUpdated = cursorWrap.getDate(DatabaseHelper.TASK_DATE_STATUS_UPDATED_COLUMN);
        String description = cursorWrap.getString(DatabaseHelper.TASK_DESCRIPTION_COLUMN);
        Integer iconId = cursorWrap.getInteger(DatabaseHelper.TASK_ICON_ID_COLUMN);

        Integer statusValue = cursorWrap.getInteger(DatabaseHelper.ID_COLUMN);
        Task.Status status =
                statusValue == null ? Task.Status.UNDONE : Task.Status.values()[statusValue];

        String title = cursorWrap.getString(DatabaseHelper.TASK_TITLE_COLUMN);
        Integer groupId = cursorWrap.getInteger(DatabaseHelper.TASK_GROUP_ID_COLUMN);

        return new Task.Builder()
                .id(id)
                .dateAlarm(dateAlarm)
                .dateCreated(dateCreated)
                .dateDeadline(dateDeadline)
                .dateStatusUpdated(dateStatusUpdated)
                .description(description)
                .iconId(iconId)
                .status(status)
                .title(title)
                .groupId(groupId)
                .build();
    }
}

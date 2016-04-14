package io.blackbricks.todomanager.database.transforms;


import android.database.Cursor;

import java.util.Date;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.model.Task;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 28/03/16.
 */
public class CursorToTask implements Func1<Cursor, Task> {
    @Override
    public Task call(Cursor cursor) {
        int taskId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID_COLUMN));

        Date taskDateCreated = new Date(cursor.getLong
                (cursor.getColumnIndex(DatabaseHelper.TASK_DATE_CREATED_COLUMN))
        );

        Date taskDateStatusUpdated = new Date(cursor.
                getLong(cursor.getColumnIndex(DatabaseHelper.TASK_DATE_STATUS_UPDATED_COLUMN))
        );

        Task.TaskStatus taskStatus = Task.TaskStatus
                .values()[cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TASK_STATUS_COLUMN))];

        String taskTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TASK_TITLE_COLUMN));

        Task task = new Task(taskId, taskDateCreated, taskDateStatusUpdated, taskStatus, taskTitle);

        int iconIdIndex = cursor.getColumnIndex(DatabaseHelper.TASK_ICON_ID_COLUMN);
        if (!cursor.isNull(iconIdIndex)) {
            int iconId = cursor.getInt(iconIdIndex);
            task.setIconId(iconId);
        }

        int alarmIndex = cursor.getColumnIndex(DatabaseHelper.TASK_DATE_ALARM_COLUMN);
        if (!cursor.isNull(alarmIndex)) {
            Date taskDateAlarm = new Date(cursor.getLong(alarmIndex));
            task.setDateAlarm(taskDateAlarm);
        }

        int deadlineIndex = cursor.getColumnIndex(DatabaseHelper.TASK_DATE_DEADLINE_COLUMN);
        if (!cursor.isNull(deadlineIndex)) {
            Date taskDateDeadline = new Date(cursor.getLong(deadlineIndex));
            task.setDateDeadline(taskDateDeadline);
        }

        int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.TASK_DESCRIPTION_COLUMN);
        if (!cursor.isNull(descriptionIndex)) {
            String taskDescription = cursor.getString(descriptionIndex);
            task.setDescription(taskDescription);
        }
        return task;
    }
}

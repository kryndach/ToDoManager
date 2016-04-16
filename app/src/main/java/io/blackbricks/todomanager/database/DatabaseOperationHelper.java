package io.blackbricks.todomanager.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.Date;

import javax.inject.Inject;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 08/04/16.
 */
public class DatabaseOperationHelper {

    @Inject
    BriteDatabase database;

    @Inject
    public DatabaseOperationHelper() {
    }

    public void updateGroupTaskCount() {
        database.executeAndTrigger("groups", "UPDATE " + DatabaseHelper.TABLE_GROUP
                + " SET " + DatabaseHelper.GROUP_TASK_COUNT_COLUMN + " = "
                + " ("
                + " SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_TASK
                + " WHERE " + DatabaseHelper.TABLE_GROUP + "." + DatabaseHelper.ID_COLUMN
                + " = "
                + DatabaseHelper.TABLE_TASK + "." + DatabaseHelper.TASK_GROUP_ID_COLUMN
                + " )");
    }

    public void deleteTask(Integer taskId) {
        database.delete("tasks",
                DatabaseHelper.ID_COLUMN + " = " + taskId);
        updateGroupTaskCount();
    }

    public void createTask(Task task) {
        ContentValues values = getTaskContentValues(task);
        database.insert("tasks", values);
        updateGroupTaskCount();
    }

    public void updateTask(Task task) {
        ContentValues values = getTaskContentValues(task);
        database.update("tasks",
                values,
                SQLiteDatabase.CONFLICT_REPLACE,
                DatabaseHelper.ID_COLUMN + " = ?",
                String.valueOf(task.getId()));
        updateGroupTaskCount();
    }

    @NonNull
    private ContentValues getTaskContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TASK_TITLE_COLUMN, task.getTitle());
        values.put(DatabaseHelper.TASK_DESCRIPTION_COLUMN, task.getDescription());
        values.put(DatabaseHelper.TASK_GROUP_ID_COLUMN, task.getGroupId());

        if(task.getDateCreated() != null) {
            values.put(DatabaseHelper.TASK_DATE_ALARM_COLUMN, task.getDateAlarm().getTime());
            values.put(DatabaseHelper.TASK_DATE_CREATED_COLUMN, task.getDateCreated().getTime());
            values.put(DatabaseHelper.TASK_DATE_DEADLINE_COLUMN, task.getDateDeadline().getTime());
            values.put(DatabaseHelper.TASK_DATE_STATUS_UPDATED_COLUMN, task.getDateStatusUpdated().getTime());
            values.put(DatabaseHelper.TASK_ICON_ID_COLUMN, task.getIconId());
            values.put(DatabaseHelper.TASK_STATUS_COLUMN, task.getStatus().getValue());
        } else {
            values.put(DatabaseHelper.TASK_DATE_CREATED_COLUMN, new Date().getTime());
            values.put(DatabaseHelper.TASK_DATE_STATUS_UPDATED_COLUMN, new Date().getTime());
            values.put(DatabaseHelper.TASK_STATUS_COLUMN, Task.Status.UNDONE.getValue());
        }

        return values;
    }
}

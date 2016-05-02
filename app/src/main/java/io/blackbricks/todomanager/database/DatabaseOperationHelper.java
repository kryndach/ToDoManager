package io.blackbricks.todomanager.database;

import android.content.ContentValues;
import android.database.Observable;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.blackbricks.todomanager.database.transforms.CursorToGroup;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskStorIOSQLitePutResolver;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yegorkryndach on 08/04/16.
 */
public class DatabaseOperationHelper {

    @Inject
    StorIOSQLite storio;

    @Inject
    public DatabaseOperationHelper() {
    }

    // Group

    public void putGroup(Group group){
        storio.put()
                .object(group)
                .prepare()
                .executeAsBlocking();
    }

    public void updateGroupTaskCount() {
        storio.executeSQL()
                .withQuery(RawQuery.builder()
                        .query("UPDATE " + DatabaseHelper.TABLE_GROUP
                                + " SET " + DatabaseHelper.GROUP_TASK_COUNT_COLUMN + " = "
                                + " ("
                                + " SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_TASK
                                + " WHERE " + DatabaseHelper.TABLE_GROUP + "." + DatabaseHelper.ID_COLUMN
                                + " = "
                                + DatabaseHelper.TABLE_TASK + "." + DatabaseHelper.TASK_GROUP_ID_COLUMN
                                + " )")
                        .build())
                .prepare()
                .executeAsBlocking();
    }

    // Task

    public void deleteTask(Integer taskId) {
        storio.delete()
                .byQuery(DeleteQuery.builder()
                        .table(DatabaseHelper.TABLE_TASK)
                        .where(DatabaseHelper.ID_COLUMN + " = ?")
                        .whereArgs(taskId)
                        .build())
                .prepare()
                .executeAsBlocking();
        updateGroupTaskCount();
    }

    public void putTask(Task task) {
        storio.put()
                .object(task)
//                .withPutResolver(new TaskStorIOSQLitePutResolver() {
//                    @NonNull
//                    @Override
//                    public ContentValues mapToContentValues(@NonNull Task object) {
//                        ContentValues values = new ContentValues();
//                        values.put(DatabaseHelper.TASK_TITLE_COLUMN, object.getTitle());
//                        values.put(DatabaseHelper.TASK_DESCRIPTION_COLUMN, object.getDescription());
//                        values.put(DatabaseHelper.TASK_GROUP_ID_COLUMN, object.getGroupId());
//
//                        if(object.getDateCreated() != null) {
//                            values.put(DatabaseHelper.TASK_DATE_ALARM_COLUMN, object.getDateAlarm().getTime());
//                            values.put(DatabaseHelper.TASK_DATE_CREATED_COLUMN, object.getDateCreated().getTime());
//                            values.put(DatabaseHelper.TASK_DATE_DEADLINE_COLUMN, object.getDateDeadline().getTime());
//                            values.put(DatabaseHelper.TASK_DATE_STATUS_UPDATED_COLUMN, object.getDateStatusUpdated().getTime());
//                            values.put(DatabaseHelper.TASK_ICON_ID_COLUMN, object.getIconId());
//                            values.put(DatabaseHelper.TASK_STATUS_COLUMN, object.getStatus().getValue());
//                        } else {
//                            values.put(DatabaseHelper.TASK_DATE_CREATED_COLUMN, new Date().getTime());
//                            values.put(DatabaseHelper.TASK_DATE_STATUS_UPDATED_COLUMN, new Date().getTime());
//                            values.put(DatabaseHelper.TASK_STATUS_COLUMN, Task.Status.UNDONE.getValue());
//                        }
//
//                        return values;
//                    }
//                })
                .prepare()
                .executeAsBlocking();
        updateGroupTaskCount();
    }
}

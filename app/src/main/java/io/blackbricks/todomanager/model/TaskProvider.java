package io.blackbricks.todomanager.model;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.database.transforms.CursorToTask;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@Singleton
public class TaskProvider {

    @Inject
    StorIOSQLite storIO;

    @Inject
    public TaskProvider() {
    }

    public Observable<List<Task>> getTasks(Filter.Type filterType, @Nullable Integer groupId) {
        String condition;
        switch (filterType) {
            case INBOX: {
                condition = DatabaseHelper.TASK_GROUP_ID_COLUMN + " IS NULL";
                break;
            }
            case TODAY: {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date todayStart = calendar.getTime();
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                Date todayEnd = calendar.getTime();
                condition = DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " > " + todayStart.getTime()
                        + " AND " + DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " < " + todayEnd.getTime();
                break;
            }
            case TOMORROW: {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date tomorrowStart = calendar.getTime();
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                Date tomorrowEnd = calendar.getTime();
                condition = DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " > " + tomorrowStart.getTime()
                        + " AND " + DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " < " + tomorrowEnd.getTime();
                break;
            }
            case WEEK: {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date weekStart = calendar.getTime();
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                Date weekEnd = calendar.getTime();
                condition = DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " > " + weekStart.getTime()
                        + " AND " + DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " < " + weekEnd.getTime();
                break;
            }
            case HOT: {
                condition = DatabaseHelper.TASK_STATUS_COLUMN + " = " + Task.Status.HOT.getValue();
                break;
            }
            case DONE: {
                condition = DatabaseHelper.TASK_STATUS_COLUMN + " = " + Task.Status.DONE.getValue();
                break;
            }
            case OVERDUE: {
                condition = DatabaseHelper.TASK_STATUS_COLUMN + " = " + Task.Status.OVERDUE.getValue();
                break;
            }
            case GROUP: {
                condition = DatabaseHelper.TASK_GROUP_ID_COLUMN + " = " + groupId;
                break;
            }
            case ALL:
            default: {
                condition = "";
                break;
            }
        }

        return storIO
                .get()
                .listOfObjects(Task.class)
                .withQuery(Query.builder()
                        .table(DatabaseHelper.TABLE_TASK)
                        .where(condition)
                        .build())
                .withGetResolver(getResolver())
                .prepare()
                .asRxObservable()
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Task> getTask(Integer taskId) {
        return storIO
                .get()
                .object(Task.class)
                .withQuery(Query.builder()
                        .table(DatabaseHelper.TABLE_TASK)
                        .where(DatabaseHelper.ID_COLUMN + " = ?")
                        .whereArgs(taskId)
                        .build())
                .withGetResolver(getResolver())
                .prepare()
                .asRxObservable()
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static GetResolver<Task> getResolver() {
        return new TaskStorIOSQLiteGetResolver() {
            @Override
            @NonNull
            public Task mapFromCursor(@NonNull Cursor cursor) {
                Task object = new Task();

                if (!cursor.isNull(cursor.getColumnIndex("date_alarm")))
                    object.dateAlarm = cursor.getLong(cursor.getColumnIndex("date_alarm"));
                if (!cursor.isNull(cursor.getColumnIndex("date_status_updated")))
                    object.dateStatusUpdated = cursor.getLong(cursor.getColumnIndex("date_status_updated"));
                if (!cursor.isNull(cursor.getColumnIndex("group_id")))
                    object.groupId = cursor.getInt(cursor.getColumnIndex("group_id"));
                if (!cursor.isNull(cursor.getColumnIndex("date_created")))
                    object.dateCreated = cursor.getLong(cursor.getColumnIndex("date_created"));
                if (!cursor.isNull(cursor.getColumnIndex("description")))
                    object.description = cursor.getString(cursor.getColumnIndex("description"));
                if (!cursor.isNull(cursor.getColumnIndex("id")))
                    object.id = cursor.getInt(cursor.getColumnIndex("id"));
                if (!cursor.isNull(cursor.getColumnIndex("date_deadline")))
                    object.dateDeadline = cursor.getLong(cursor.getColumnIndex("date_deadline"));
                if (!cursor.isNull(cursor.getColumnIndex("icon_id")))
                    object.iconId = cursor.getInt(cursor.getColumnIndex("icon_id"));
                else
                    object.iconId = null;
                if (!cursor.isNull(cursor.getColumnIndex("title")))
                    object.title = cursor.getString(cursor.getColumnIndex("title"));
                if (!cursor.isNull(cursor.getColumnIndex("status")))
                    object.status = cursor.getInt(cursor.getColumnIndex("status"));

                return object;
            }
        };
    }
}

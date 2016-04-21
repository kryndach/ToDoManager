package io.blackbricks.todomanager.model;

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
    BriteDatabase database;

    @Inject
    public TaskProvider() {
    }

    public Observable<List<Task>> getTasks(Filter.Type filterType) {
        String condition;
        switch (filterType) {
            case INBOX: {
                condition = " WHERE " + DatabaseHelper.TASK_GROUP_ID_COLUMN + " IS NULL";
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
                condition = " WHERE " + DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " > " + todayStart.getTime()
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
                condition = " WHERE " + DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " > " + tomorrowStart.getTime()
                        + " AND " + DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " < " + tomorrowEnd.getTime();
                break;
            }
            case WEEK: {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, 0);
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date weekStart = calendar.getTime();
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                Date weekEnd = calendar.getTime();
                condition = " WHERE " + DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " > " + weekStart.getTime()
                        + " AND " + DatabaseHelper.TASK_DATE_DEADLINE_COLUMN + " < " + weekEnd.getTime();
                break;
            }
            case HOT: {
                condition = " WHERE " + DatabaseHelper.TASK_STATUS_COLUMN + " = " + Task.Status.HOT.getValue();
                break;
            }
            case DONE: {
                condition = " WHERE " + DatabaseHelper.TASK_STATUS_COLUMN + " = " + Task.Status.DONE.getValue();
                break;
            }
            case OVERDUE: {
                condition = " WHERE " + DatabaseHelper.TASK_STATUS_COLUMN + " = " + Task.Status.OVERDUE.getValue();
                break;
            }
            default: {
                condition = "";
                break;
            }
        }
        return database.createQuery(DatabaseHelper.TABLE_TASK,
                "SELECT * FROM ", DatabaseHelper.TABLE_TASK, condition)
                .mapToList(new CursorToTask())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Task>> getTasks(String groupId) {
        String condition = " WHERE " + DatabaseHelper.TASK_GROUP_ID_COLUMN + " = " + groupId;
        return database.createQuery(DatabaseHelper.TABLE_TASK,
                "SELECT * FROM ", DatabaseHelper.TABLE_TASK, condition)
                .mapToList(new CursorToTask())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Task> getTask(String taskId) {
        String condition = " WHERE " + DatabaseHelper.ID_COLUMN + " = " + taskId;
        return database.createQuery(DatabaseHelper.TABLE_TASK,
                "SELECT * FROM ", DatabaseHelper.TABLE_TASK, condition)
                .mapToOne(new CursorToTask())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

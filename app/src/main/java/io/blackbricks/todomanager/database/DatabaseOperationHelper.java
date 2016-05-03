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

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.blackbricks.todomanager.database.transforms.CursorToGroup;
import io.blackbricks.todomanager.events.TaskPuttedEvent;
import io.blackbricks.todomanager.events.TaskRemovedEvent;
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
    EventBus eventBus;

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
                        .affectsTables(DatabaseHelper.TABLE_GROUP)
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
        eventBus.post(new TaskRemovedEvent());
        updateGroupTaskCount();
    }

    public void putTask(Task task) {
        storio.put()
                .object(task)
                .prepare()
                .executeAsBlocking();
        eventBus.post(new TaskPuttedEvent());
        updateGroupTaskCount();
    }
}

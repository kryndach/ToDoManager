package io.blackbricks.todomanager.database;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.blackbricks.todomanager.events.GroupInsertedEvent;
import io.blackbricks.todomanager.events.GroupUpdatedEvent;
import io.blackbricks.todomanager.events.GroupRemovedEvent;
import io.blackbricks.todomanager.events.GroupListUpdatedEvent;
import io.blackbricks.todomanager.events.TaskInsertedEvent;
import io.blackbricks.todomanager.events.TaskUpdatedEvent;
import io.blackbricks.todomanager.events.TaskRemovedEvent;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskProvider;

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

    public void putGroup(Group group) {
        PutResult putResult = storio.put()
                .object(group)
                .prepare()
                .executeAsBlocking();

        if (putResult.wasInserted()) {
            group = storio
                    .get()
                    .object(Group.class)
                    .withQuery(Query.builder()
                            .table(DatabaseHelper.TABLE_GROUP)
                            .where(DatabaseHelper.ID_COLUMN + " = ?")
                            .whereArgs(putResult.insertedId().intValue())
                            .build())
                    .prepare()
                    .executeAsBlocking();
            eventBus.post(new GroupInsertedEvent(group));
        } else {
            eventBus.post(new GroupUpdatedEvent(group));
        }
    }

    public void deleteGroup(Integer groupId) {
        storio.delete()
                .byQuery(DeleteQuery.builder()
                        .table(DatabaseHelper.TABLE_GROUP)
                        .where(DatabaseHelper.ID_COLUMN + " = ?")
                        .whereArgs(groupId)
                        .build())
                .prepare()
                .executeAsBlocking();
        eventBus.post(new GroupRemovedEvent(groupId));
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
        eventBus.post(new GroupListUpdatedEvent());
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
        eventBus.post(new TaskRemovedEvent(taskId));
        updateGroupTaskCount();
    }

    public void putTask(Task task) {
        PutResult putResult = storio.put()
                .object(task)
                .prepare()
                .executeAsBlocking();

        if (putResult.wasInserted()) {
            task = storio
                    .get()
                    .object(Task.class)
                    .withQuery(Query.builder()
                            .table(DatabaseHelper.TABLE_TASK)
                            .where(DatabaseHelper.ID_COLUMN + " = ?")
                            .whereArgs(putResult.insertedId().intValue())
                            .build())
                    .withGetResolver(TaskProvider.getResolver())
                    .prepare()
                    .executeAsBlocking();
            eventBus.post(new TaskInsertedEvent(task));
        } else {
            eventBus.post(new TaskUpdatedEvent(task));
        }

        updateGroupTaskCount();
    }
}

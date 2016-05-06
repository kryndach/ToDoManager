package io.blackbricks.todomanager;

import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.task.TaskActivity;
import io.blackbricks.todomanager.taskList.TaskListActivity;

/**
 * A simple helper class that helps to create and launch Intents. It checks if we our device is a
 * phone or a tablet app.
 *
 * @author Hannes Dorfmann
 */

@Singleton
public class IntentStarter {

    @Inject
    EventBus eventBus;

    @Inject
    public IntentStarter() {
    }

    private boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.tablet);
    }

    public void createTask(Context context) {
        Intent i = new Intent(context, TaskActivity.class);
        i.putExtra(TaskActivity.KEY_TITLE, "Create task");
        context.startActivity(i);
    }

    public void editTask(Context context, Integer taskId) {
        Intent i = new Intent(context, TaskActivity.class);
        i.putExtra(TaskActivity.KEY_TASK_ID, taskId);
        i.putExtra(TaskActivity.KEY_TITLE, "Edit task");
        context.startActivity(i);
    }

}

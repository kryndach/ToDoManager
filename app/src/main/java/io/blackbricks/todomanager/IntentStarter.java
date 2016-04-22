package io.blackbricks.todomanager;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.Task;
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
    public IntentStarter() {
    }

    private boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.tablet);
    }

    public void showTaskList(Context context, Filter filter) {
        Intent i = new Intent(context, TaskListActivity.class);
        i.putExtra(TaskListActivity.KEY_FILTER, filter);
        context.startActivity(i);
    }

    public void showTaskList(Context context, Integer groupId) {
        Intent i = new Intent(context, TaskListActivity.class);
        i.putExtra(TaskListActivity.KEY_FILTER, Filter.Type.GROUP);
        i.putExtra(TaskListActivity.KEY_GROUP_ID, groupId);
        context.startActivity(i);
    }

}

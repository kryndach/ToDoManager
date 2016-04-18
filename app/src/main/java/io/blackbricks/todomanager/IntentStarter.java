package io.blackbricks.todomanager;

import android.content.Context;
import android.content.Intent;

import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.taskList.TaskListActivity;

/**
 * A simple helper class that helps to create and launch Intents. It checks if we our device is a
 * phone or a tablet app.
 *
 * @author Hannes Dorfmann
 */
// TODO make it injectable with dagger
public class IntentStarter {

    private boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.tablet);
    }

    public void showTaskList(Context context, Filter filter) {
        Intent i = new Intent(context, TaskListActivity.class);
        i.putExtra(TaskListActivity.KEY_FILTER, filter);
        context.startActivity(i);
    }

    public void showTaskList(Context context, Group group) {
        Intent i = new Intent(context, TaskListActivity.class);
        i.putExtra(TaskListActivity.KEY_GROUP, group);
        context.startActivity(i);
    }

    public void showTaskList(Context context, OptionalMenuItem.Type optionalType) {
        Intent i = new Intent(context, TaskListActivity.class);
        i.putExtra(TaskListActivity.KEY_OPTIONAL_TYPE, optionalType);
        context.startActivity(i);
    }

}

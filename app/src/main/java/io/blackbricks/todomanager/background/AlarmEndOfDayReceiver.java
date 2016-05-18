package io.blackbricks.todomanager.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.database.DatabaseOperationHelper;

/**
 * Created by yegorkryndach on 18/05/16.
 */
public class AlarmEndOfDayReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ToDoManagerApp.getAppComponent().dbOperationHelper().updateTaskOverdue();
    }
}

package io.blackbricks.todomanager.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskProvider;
import rx.functions.Action1;

/**
 * Created by yegorkryndach on 16/05/16.
 */

@Singleton
public class Alarm {

    @Inject
    public Alarm(final Context context, TaskProvider taskProvider) {
        taskProvider.getTasks(Filter.Type.ALL, null).subscribe(new Action1<List<Task>>() {
            @Override
            public void call(List<Task> tasks) {
                for(Task task : tasks) {
                    setAlarm(context, task);
                }
            }
        });


    }

    public void setAlarm(Context context, Task task) {
        if(task.getDateAlarm() == null) {
            return;
        }

        if(task.getDateAlarm().getTime() < System.currentTimeMillis()) {
            return;
        }

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        bundle.putString("type", "taskAlarm");
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task.getId(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT );

        am.cancel(pendingIntent);
        am.set(AlarmManager.RTC_WAKEUP, task.getDateAlarm().getTime(), pendingIntent);
    }

    public void cancelAlarm(Context context, Task task) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task.getId(),
                intent, PendingIntent.FLAG_CANCEL_CURRENT );

        am.cancel(pendingIntent);
    }

}

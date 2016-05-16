package io.blackbricks.todomanager.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.ToDoManagerApp;

/**
 * Created by yegorkryndach on 16/05/16.
 */

@Singleton
public class Alarm {

    @Inject
    public Alarm() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT );

        am.cancel(pendingIntent);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, pendingIntent);
    }


}

package io.blackbricks.todomanager.background;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.task.TaskActivity;
import io.blackbricks.todomanager.taskList.TaskListActivity;

/**
 * Created by yegorkryndach on 16/05/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFY_ID = 101;

    @Override
    public void onReceive(Context context, Intent intent) {

        String type = intent.getExtras().getString("type");
        if(type == null){
            return;
        }
        if(type.equals("taskAlarm")) {
            Task task = intent.getExtras().getParcelable("task");
            if (task == null) {
                return;
            }

            Intent notificationIntent = new Intent(context, TaskActivity.class);
            notificationIntent.putExtra(TaskActivity.KEY_TASK_ID, task.getId());
            notificationIntent.putExtra(TaskActivity.KEY_TITLE, "Edit task");

            PendingIntent contentIntent = PendingIntent.getActivity(context,
                    0, notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Notification.Builder builder = new Notification.Builder(context);

            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_add_black_24dp)
                    .setTicker("Alarm!")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle("Alarm!")
                    .setContentText(task.getTitle());
            Uri ringURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(ringURI);
            builder.setOngoing(true);

            if (task.getIconId() != null) {
                builder.setSmallIcon(task.getIconId());
            }

            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFY_ID, notification);
        } else {

        }
    }
}

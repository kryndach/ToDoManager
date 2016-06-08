package io.blackbricks.todomanager.utils.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;

/**
 * Created by yegorkryndach on 08/06/16.
 */
public class DateConverter {
    public static String alertConvert(Date date) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(ToDoManagerApp.getContext().getString(R.string.AlarmDateFormat));
        return sdf.format(date);
    }

    public static String deadlineConvert(Date date) {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(ToDoManagerApp.getContext().getString(R.string.DeadlineDateFormat));
        return sdf.format(date);
    }
}

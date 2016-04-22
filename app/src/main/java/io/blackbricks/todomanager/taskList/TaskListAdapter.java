package io.blackbricks.todomanager.taskList;

import android.content.Context;

import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import java.util.ArrayList;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 22/04/16.
 */
public class TaskListAdapter extends SupportAnnotatedAdapter {

    ArrayList<Task> taskList;

    public TaskListAdapter(Context context) {
        super(context);
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

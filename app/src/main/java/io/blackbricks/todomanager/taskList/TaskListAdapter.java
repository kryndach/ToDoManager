package io.blackbricks.todomanager.taskList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import java.util.ArrayList;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 22/04/16.
 */
public class TaskListAdapter extends SupportAnnotatedAdapter implements TaskListAdapterBinder {

    public interface TaskClickListener {
        public void onTaskClicked(Task task);
    }

    @ViewType(layout = R.layout.list_task_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.icon, name = "icon", type = ImageView.class),
                    @ViewField(id = R.id.title, name = "title", type = TextView.class),
                    @ViewField(id = R.id.description, name = "description", type = TextView.class),
            })
    public final int taskItem = 0;

    ArrayList<Task> taskList;

    private TaskClickListener taskClickListener;

    public TaskListAdapter(Context context, ArrayList<Task> taskList, TaskClickListener taskClickListener) {
        super(context);
        this.taskList = taskList;
        this.taskClickListener = taskClickListener;
    }

    public void setTaskClickListener(TaskClickListener taskClickListener) {
        this.taskClickListener = taskClickListener;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int getItemCount() {
        return taskList == null ? 0 : taskList.size();
    }

    @Override
    public void initViewHolder(TaskListAdapterHolders.TaskItemViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(TaskListAdapterHolders.TaskItemViewHolder vh, int position) {
        final Task task = taskList.get(position);
        if (task.getIconId() != null) {
            vh.icon.setImageResource(task.getIconId());
        }
        vh.title.setText(task.getTitle());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskClickListener.onTaskClicked(task);
            }
        });
    }
}

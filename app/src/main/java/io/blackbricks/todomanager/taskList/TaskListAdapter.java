package io.blackbricks.todomanager.taskList;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import java.util.ArrayList;
import java.util.List;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 22/04/16.
 */
public class TaskListAdapter extends SupportAnnotatedAdapter implements TaskListAdapterBinder,
        SwipeItemMangerInterface, SwipeAdapterInterface {

    public interface TaskClickListener {
        public void onTaskClicked(Task task, int position);
    }

    public interface TaskDoneListener {
        public void onTaskDone(Task task, int position);
    }

    public interface TaskHotListener {
        public void onTaskHot(Task task, int position);
    }

    public interface TaskDeleteListener {
        public void onTaskDelete(Task task, int position);
    }

    public SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    @ViewType(layout = R.layout.list_task_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.icon, name = "icon", type = ImageView.class),
                    @ViewField(id = R.id.title, name = "title", type = TextView.class),
                    @ViewField(id = R.id.swipe_layout, name = "swipeLayout", type = SwipeLayout.class),
                    @ViewField(id = R.id.done, name = "done", type = LinearLayout.class),
                    @ViewField(id = R.id.done_background, name = "done_background", type = LinearLayout.class),
                    @ViewField(id = R.id.done_title, name = "done_title", type = TextView.class),
                    @ViewField(id = R.id.hot, name = "hot", type = LinearLayout.class),
                    @ViewField(id = R.id.hot_background, name = "hot_background", type = LinearLayout.class),
                    @ViewField(id = R.id.hot_title, name = "hot_title", type = TextView.class),
                    @ViewField(id = R.id.delete, name = "delete", type = LinearLayout.class),
                    @ViewField(id = R.id.delete_title, name = "delete_title", type = TextView.class),
            })
    public final int taskItem = 0;

    ArrayList<Task> taskList;

    private TaskClickListener taskClickListener;
    private TaskDoneListener taskDoneListener;
    private TaskHotListener taskHotListener;
    private TaskDeleteListener taskDeleteListener;


    public TaskListAdapter(Context context, ArrayList<Task> taskList,
                           TaskClickListener taskClickListener, TaskDoneListener taskDoneListener,
                           TaskHotListener taskHotListener, TaskDeleteListener taskDeleteListener) {
        super(context);
        this.taskList = taskList;
        this.taskClickListener = taskClickListener;
        this.taskDoneListener = taskDoneListener;
        this.taskHotListener = taskHotListener;
        this.taskDeleteListener = taskDeleteListener;
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
    public void bindViewHolder(TaskListAdapterHolders.TaskItemViewHolder vh, final int position) {
        final Task task = taskList.get(position);
        if (task.getIconId() != null) {
            vh.icon.setImageResource(task.getIconId());
        } else {
            vh.icon.setImageResource(R.drawable.briefcase_black);
        }
        vh.title.setText(task.getTitle());

        if(task.getStatus() == Task.Status.HOT) {
            vh.hot_background.setBackgroundResource(R.color.unhot);
            vh.hot_title.setText(R.string.unhot);
        } else {
            vh.hot_background.setBackgroundResource(R.color.hot);
            vh.hot_title.setText(R.string.hot);
        }

        if(task.getStatus() == Task.Status.DONE) {
            vh.done_background.setBackgroundResource(R.color.undone);
            vh.done_title.setText(R.string.undone);
        } else {
            vh.done_background.setBackgroundResource(R.color.done);
            vh.done_title.setText(R.string.done);
        }

        vh.delete_title.setText(R.string.delete);

        mItemManger.bindView(vh.itemView, position);
        vh.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskClickListener.onTaskClicked(task, position);
            }
        });
        vh.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDoneListener.onTaskDone(task, position);
            }
        });
        vh.hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskHotListener.onTaskHot(task, position);
            }
        });
        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDeleteListener.onTaskDelete(task, position);
            }
        });
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        mItemManger.setMode(mode);
    }
}

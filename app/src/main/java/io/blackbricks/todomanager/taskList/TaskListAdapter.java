package io.blackbricks.todomanager.taskList;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.utils.adapter.SectionedAdapter;

/**
 * Created by yegorkryndach on 22/04/16.
 */
public class TaskListAdapter extends SectionedAdapter implements TaskListAdapterBinder,
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
                    @ViewField(id = R.id.deadline, name = "deadline", type = TextView.class),
                    @ViewField(id = R.id.hot_status_icon, name = "hot_status_icon", type = ImageView.class),
                    @ViewField(id = R.id.swipe_layout, name = "swipeLayout", type = SwipeLayout.class),
                    @ViewField(id = R.id.done, name = "done", type = FrameLayout.class),
                    @ViewField(id = R.id.done_background, name = "done_background", type = LinearLayout.class),
                    @ViewField(id = R.id.done_title, name = "done_title", type = TextView.class),
                    @ViewField(id = R.id.done_image, name = "done_image", type = ImageView.class),
                    @ViewField(id = R.id.hot, name = "hot", type = FrameLayout.class),
                    @ViewField(id = R.id.hot_background, name = "hot_background", type = LinearLayout.class),
                    @ViewField(id = R.id.hot_title, name = "hot_title", type = TextView.class),
                    @ViewField(id = R.id.hot_image, name = "hot_image", type = ImageView.class),
                    @ViewField(id = R.id.delete, name = "delete", type = FrameLayout.class),
                    @ViewField(id = R.id.delete_title, name = "delete_title", type = TextView.class),
            })
    public final int taskItem = 0;

    @ViewType(layout = R.layout.list_task_section_header,
            initMethod = true,
            views = {})
    public final int sectionHeader = ITEM_SECTION_HEADER;

    ArrayList<Pair<String, ArrayList<Task>>> sectionList;

    private TaskClickListener taskClickListener;
    private TaskDoneListener taskDoneListener;
    private TaskHotListener taskHotListener;
    private TaskDeleteListener taskDeleteListener;

    private Context context;

    public TaskListAdapter(Context context, ArrayList<Pair<String,
            ArrayList<Task>>> sectionList, TaskClickListener taskClickListener,
                           TaskDoneListener taskDoneListener, TaskHotListener taskHotListener,
                           TaskDeleteListener taskDeleteListener) {
        super(context);
        this.sectionList = sectionList;
        this.taskClickListener = taskClickListener;
        this.taskDoneListener = taskDoneListener;
        this.taskHotListener = taskHotListener;
        this.taskDeleteListener = taskDeleteListener;
        this.context = context;
    }

    public void setTaskClickListener(TaskClickListener taskClickListener) {
        this.taskClickListener = taskClickListener;
    }

    public ArrayList<Pair<String, ArrayList<Task>>> getSectionList() {
        return sectionList;
    }

    public void setSectionList(ArrayList<Pair<String, ArrayList<Task>>> sectionList) {
        this.sectionList = sectionList;
    }

    @Override
    protected int getSectionItemCount(int section) {
        return 0;
    }

    @Override
    protected boolean supportHeader(int section) {
        return false;
    }

    @Override
    protected int getSectionCount() {
        return 0;
    }

    @Override
    protected int getItemViewTypeBySection(int section) {
        return 0;
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
            vh.hot_image.setImageResource(R.drawable.fire_cross);
            vh.hot_status_icon.setVisibility(View.VISIBLE);
            vh.title.setTextColor(ContextCompat.getColor(context, R.color.hot));
        } else {
            vh.hot_background.setBackgroundResource(R.color.hot);
            vh.hot_title.setText(R.string.hot);
            vh.hot_image.setImageResource(R.drawable.fire);
            vh.hot_status_icon.setVisibility(View.GONE);
            vh.title.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        if(task.getDateDeadline() != null) {
            SimpleDateFormat format = new SimpleDateFormat(context.getString(R.string.TaskDeadlineDateFormat));
            String textDeadline = format.format(task.getDateDeadline());
            vh.deadline.setText(textDeadline);
            vh.deadline.setVisibility(View.VISIBLE);
        } else {
            vh.deadline.setVisibility(View.GONE);
        }

        if(task.getStatus() == Task.Status.DONE) {
            vh.done_background.setBackgroundResource(R.color.undone);
            vh.done_title.setText(R.string.undone);
            vh.done_image.setImageResource(R.drawable.close);
        } else {
            vh.done_background.setBackgroundResource(R.color.done);
            vh.done_title.setText(R.string.done);
            vh.done_image.setImageResource(R.drawable.check);
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

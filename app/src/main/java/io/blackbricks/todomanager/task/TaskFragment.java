package io.blackbricks.todomanager.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;

import butterknife.Bind;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.task.model.TaskPresentation;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskFragment extends BaseLceFragment<LinearLayout, TaskPresentation, TaskView, TaskPresenter>
        implements TaskView {

    @Arg(required = false)
    Integer taskId;

    @Arg(required = false)
    Integer groupId;

    @Bind(R.id.task_view_edit_text_title)
    EditText editTextTitle;
    @Bind(R.id.task_view_title_clear_button)
    ImageButton imageButtonClearTitle;

    @Bind(R.id.task_view_edit_text_description)
    EditText editTextDescription;
    @Bind(R.id.task_view_description_clear_button)
    ImageButton imageButtonClearDescription;

    @Bind(R.id.task_view_group)
    View viewGroup;
    @Bind(R.id.task_view_group_name)
    TextView textViewGroupName;
    @Bind(R.id.task_view_group_clear_button)
    ImageButton imageButtonClearGroup;

    @Bind(R.id.task_view_alarm)
    View viewAlarm;
    @Bind(R.id.task_view_alarm_text)
    TextView textViewAlarmText;
    @Bind(R.id.task_view_alarm_clear_button)
    ImageButton imageButtonClearAlarm;

    @Bind(R.id.task_view_deadline)
    View viewDeadline;
    @Bind(R.id.task_view_deadline_text)
    TextView textViewDeadlineText;
    @Bind(R.id.task_view_deadline_clear_button)
    ImageButton imageButtonClearDeadline;

    @Bind(R.id.task_view_icon)
    View viewIcon;
    @Bind(R.id.task_view_icon_text)
    TextView textViewIconText;
    @Bind(R.id.task_view_icon_clear_button)
    ImageButton imageButtonClearIcon;
    @Bind(R.id.task_view_icon_image)
    ImageView imageViewIcon;

    @Bind(R.id.task_view_photo)
    View viewPhoto;
    @Bind(R.id.task_view_photo_list)
    RecyclerView photoList;

    private TaskComponent taskComponent;
    private TaskPresentation taskPresentation;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_task;
    }

    @Override
    public LceViewState<TaskPresentation, TaskView> createViewState() {
        return new ParcelableDataLceViewState<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public TaskPresentation getData() {
        return taskPresentation;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public TaskPresenter createPresenter() {
        return taskComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        taskComponent = DaggerTaskComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .databaseModule(new DatabaseModule())
                .toDoManagerModule(new ToDoManagerModule(ToDoManagerApp.get(this.getActivity())))
                .build();
        taskComponent.inject(this);
    }

    @Override
    public void setData(TaskPresentation data) {
        this.taskPresentation = data;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadTask(taskId, groupId);
    }

    public void done() {

    }

    @Override
    public void updateTitle() {

    }

    @Override
    public void updateDescription() {

    }

    @Override
    public void updateGroup() {

    }

    @Override
    public void updateAlert() {

    }

    @Override
    public void updateDeadline() {

    }

    @Override
    public void updateIcon() {

    }

    @Override
    public void updateAttachments() {

    }
}

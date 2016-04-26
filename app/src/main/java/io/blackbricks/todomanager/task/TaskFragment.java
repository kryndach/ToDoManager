package io.blackbricks.todomanager.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.task.model.TaskPresentation;
import io.blackbricks.todomanager.ui.GroupListAdapter;
import io.blackbricks.todomanager.ui.IconListAdapter;
import io.blackbricks.todomanager.ui.ImageListAdapter;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskFragment extends BaseLceFragment<FrameLayout, TaskPresentation, TaskView, TaskPresenter>
        implements TaskView {

    @Arg(required = false)
    Integer taskId;

    @Arg(required = false)
    Integer groupId;

    @Bind(R.id.title_edit_text)
    EditText titleEditText;
    @Bind(R.id.title_clear_view)
    ViewGroup titleClearView;

    @Bind(R.id.description_edit_text)
    EditText descriptionEditText;
    @Bind(R.id.description_clear_view)
    ViewGroup descriptionClearView;

    @Bind(R.id.group_view)
    ViewGroup groupView;
    @Bind(R.id.group_text_view)
    TextView groupTextView;
    @Bind(R.id.group_clear_view)
    ViewGroup groupClearView;

    @Bind(R.id.alarm_view)
    ViewGroup alarmView;
    @Bind(R.id.alarm_text_view)
    TextView alarmTextView;
    @Bind(R.id.alarm_clear_view)
    ViewGroup alarmClearView;

    @Bind(R.id.deadline_view)
    ViewGroup deadlineView;
    @Bind(R.id.deadline_text_view)
    TextView deadlineTextView;
    @Bind(R.id.deadline_clear_view)
    ViewGroup deadlineClearView;

    @Bind(R.id.icon_view)
    ViewGroup iconView;
    @Bind(R.id.icon_text_view)
    TextView iconTextView;
    @Bind(R.id.icon_clear_view)
    ViewGroup iconClearView;
    @Bind(R.id.icon_image)
    ImageView iconImageView;

    @Bind(R.id.photo_view)
    ViewGroup photoView;
    @Bind(R.id.photo_list)
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

    // User actions

    @OnClick(R.id.title_clear_view)
    void onClickClearTitle() {
        presenter.clearTitle(taskPresentation);
    }

    @OnTextChanged(value = R.id.title_edit_text)
    void onTextChangedTitle(CharSequence text){
        presenter.setTitle(taskPresentation, text);
    }

    // View interface implementation

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

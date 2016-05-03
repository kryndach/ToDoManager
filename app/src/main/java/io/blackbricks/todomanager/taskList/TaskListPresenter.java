package io.blackbricks.todomanager.taskList;

import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.events.TaskPuttedEvent;
import io.blackbricks.todomanager.events.TaskRemovedEvent;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.taskList.model.TaskListPresentation;
import io.blackbricks.todomanager.taskList.model.TaskListPresentationProvider;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListPresenter extends BaseRxLcePresenter<TaskListView, TaskListPresentation> {

    @Inject
    EventBus eventBus;

    private TaskListPresentationProvider taskListPresentationProvider;

    @Inject
    public TaskListPresenter(TaskListPresentationProvider taskListPresentationProvider) {
        this.taskListPresentationProvider = taskListPresentationProvider;
    }

    public void loadTaskList(Filter.Type type, @Nullable Integer groupId, boolean pullToRefresh) {
        subscribe(taskListPresentationProvider.getTaskListPresentation(type, groupId), pullToRefresh);
    }

    @Override
    public void attachView(TaskListView view) {
        super.attachView(view);
        eventBus.register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        eventBus.unregister(this);
    }

    @Subscribe
    void onTaskPuttedEvent(TaskPuttedEvent event) {
        if (isViewAttached()) {
            getView().loadData(false);
        }
    }

    @Subscribe
    void onTaskRemovedEvent(TaskRemovedEvent event) {
        if (isViewAttached()) {
            getView().loadData(false);
        }
    }
}

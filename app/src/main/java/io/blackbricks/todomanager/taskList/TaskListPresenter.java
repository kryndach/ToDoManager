package io.blackbricks.todomanager.taskList;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.TaskProvider;
import io.blackbricks.todomanager.taskList.model.TaskListPresentation;
import io.blackbricks.todomanager.taskList.model.TaskListPresentationProvider;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListPresenter extends BaseRxLcePresenter<TaskListView, TaskListPresentation> {

    private TaskListPresentationProvider taskListPresentationProvider;

    @Inject
    public TaskListPresenter(TaskListPresentationProvider taskListPresentationProvider) {
        this.taskListPresentationProvider = taskListPresentationProvider;
    }

    public void loadTaskList(Filter.Type type, String groupId) {
        subscribe(taskListPresentationProvider.getTaskListPresentation(type, groupId), false);
    }

}

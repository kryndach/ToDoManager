package io.blackbricks.todomanager.taskList;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import io.blackbricks.todomanager.taskList.model.TaskListPresentation;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public interface TaskListView extends MvpLceView<TaskListPresentation> {
    void done();
}

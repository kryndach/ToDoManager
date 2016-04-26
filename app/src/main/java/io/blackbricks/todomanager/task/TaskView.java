package io.blackbricks.todomanager.task;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import io.blackbricks.todomanager.task.model.TaskPresentation;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public interface TaskView extends MvpLceView<TaskPresentation>{
    void updateTitle();
    void updateDescription();
    void updateGroup();
    void updateAlert();
    void updateDeadline();
    void updateIcon();
    void updateAttachments();
}

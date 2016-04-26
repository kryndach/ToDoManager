package io.blackbricks.todomanager.task.model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.blackbricks.todomanager.model.Attachment;
import io.blackbricks.todomanager.model.AttachmentProvider;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.GroupProvider;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskProvider;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func3;

/**
 * Created by yegorkryndach on 25/04/16.
 */
public class TaskPresentationProvider {

    @Inject
    TaskProvider taskProvider;

    @Inject
    GroupProvider groupProvider;

    @Inject
    AttachmentProvider attachmentProvider;

    @Inject
    public TaskPresentationProvider() {
    }

    public Observable<TaskPresentation> getTaskPresentation(@Nullable Integer taskId, @Nullable Integer groupId) {
        Observable<Task> taskObservable;
        Observable<ArrayList<Attachment>> attachmentListObservable;
        if (taskId != null) {
            taskObservable = taskProvider.getTask(taskId);
            attachmentListObservable = attachmentProvider.getAttachments(taskId)
                    .map(new Func1<List<Attachment>, ArrayList<Attachment>>() {
                        @Override
                        public ArrayList<Attachment> call(List<Attachment> attachments) {
                            return new ArrayList<>(attachments);
                        }
                    });
        } else {
            taskObservable = Observable.just(new Task.Builder().build());
            attachmentListObservable = Observable.just(new ArrayList<Attachment>());
        }

        Observable<Group> groupObservable;
        if (groupId != null) {
            groupObservable = groupProvider.getGroup(groupId);
        } else {
            groupObservable = Observable.just(new Group.Builder().build());
        }

        return Observable.combineLatest(taskObservable, groupObservable, attachmentListObservable,
                new Func3<Task, Group, ArrayList<Attachment>, TaskPresentation>() {
                    @Override
                    public TaskPresentation call(Task task, Group group, ArrayList<Attachment> attachments) {
                        return new TaskPresentation.Builder()
                                .task(task)
                                .group(group)
                                .attachments(attachments)
                                .removedAttachments(new ArrayList<Attachment>())
                                .build();
                    }
                });
    }
}

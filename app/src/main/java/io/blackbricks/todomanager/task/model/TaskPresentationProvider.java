package io.blackbricks.todomanager.task.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.File;
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
        Observable<ArrayList<AttachmentPresentation>> attachmentListObservable;
        if (taskId != null) {
            taskObservable = taskProvider.getTask(taskId);
            attachmentListObservable = attachmentProvider.getAttachments(taskId)
                    .flatMap(new Func1<List<Attachment>, Observable<Attachment>>() {
                        @Override
                        public Observable<Attachment> call(List<Attachment> attachments) {
                            return Observable.from(attachments);
                        }
                    })
                    .map(new Func1<Attachment, AttachmentPresentation>() {
                        @Override
                        public AttachmentPresentation call(Attachment attachment) {
                            String path = attachment.getPath();
                            File file = new File(path);
                            Bitmap bitmap = null;
                            if (file.exists()) {
                                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            }
                            return new AttachmentPresentation.Builder()
                                    .attachment(attachment)
                                    .bitmap(bitmap)
                                    .build();
                        }
                    })
                    .toList()
                    .map(new Func1<List<AttachmentPresentation>, ArrayList<AttachmentPresentation>>() {
                        @Override
                        public ArrayList<AttachmentPresentation> call(List<AttachmentPresentation> attachmentPresentations) {
                            return new ArrayList<>(attachmentPresentations);
                        }
                    });
        } else {
            taskObservable = Observable.just(new Task.Builder().build());
            attachmentListObservable = Observable.just(new ArrayList<AttachmentPresentation>());
        }

        Observable<Group> groupObservable;
        if (groupId != null) {
            groupObservable = groupProvider.getGroup(groupId);
        } else {
            groupObservable = Observable.just(new Group.Builder().build());
        }

        return Observable.combineLatest(taskObservable, groupObservable, attachmentListObservable,
                new Func3<Task, Group, ArrayList<AttachmentPresentation>, TaskPresentation>() {
                    @Override
                    public TaskPresentation call(Task task, Group group, ArrayList<AttachmentPresentation> attachments) {
                        return new TaskPresentation.Builder()
                                .task(task)
                                .group(group)
                                .attachmentPresentations(attachments)
                                .removedAttachmentPresentations(new ArrayList<AttachmentPresentation>())
                                .build();
                    }
                });
    }
}

package io.blackbricks.todomanager.model;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.database.transforms.CursorToAttachment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@Singleton
public class AttachmentProvider {

    @Inject
    BriteDatabase database;

    @Inject
    public AttachmentProvider() {
    }

    public Observable<List<Attachment>> getAttachments(String taskId){
        return database.createQuery(DatabaseHelper.TABLE_ATTACHMENT,
                "SELECT * FROM ", DatabaseHelper.TABLE_ATTACHMENT,
                " WHERE ", DatabaseHelper.ATTACHMENT_TASK_ID_COLUMN + " = " + taskId)
                .mapToList(new CursorToAttachment())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

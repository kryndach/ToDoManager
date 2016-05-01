package io.blackbricks.todomanager.model;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.database.transforms.CursorToAttachment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@Singleton
public class AttachmentProvider {

    @Inject
    BriteDatabase database;

    @Inject
    StorIOSQLite storIO;

    @Inject
    public AttachmentProvider() {
    }

    public Observable<List<Attachment>> getAttachments(Integer taskId){
        return database.createQuery(DatabaseHelper.TABLE_ATTACHMENT,
                "SELECT * FROM " + DatabaseHelper.TABLE_ATTACHMENT +
                " WHERE " + DatabaseHelper.ATTACHMENT_TASK_ID_COLUMN + " = " + taskId)
                .mapToList(new CursorToAttachment())
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Attachment> getAttachment(Integer attachmentId) {
        String condition = " WHERE " + DatabaseHelper.ID_COLUMN + " = " + attachmentId;
        return database.createQuery(DatabaseHelper.TABLE_ATTACHMENT,
                "SELECT * FROM " + DatabaseHelper.TABLE_ATTACHMENT + condition)
                .mapToOne(new CursorToAttachment())
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }
}

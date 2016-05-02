package io.blackbricks.todomanager.model;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;
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

    public Observable<List<Attachment>> getAttachments(Integer taskId) {
        return storIO
                .get()
                .listOfObjects(Attachment.class)
                .withQuery(Query.builder()
                        .table(DatabaseHelper.TABLE_ATTACHMENT)
                        .where(DatabaseHelper.ATTACHMENT_TASK_ID_COLUMN + " = ?")
                        .whereArgs(taskId)
                        .build())
                .prepare()
                .asRxObservable()
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Attachment> getAttachment(Integer attachmentId) {
        return storIO
                .get()
                .object(Attachment.class)
                .withQuery(Query.builder()
                        .table(DatabaseHelper.TABLE_ATTACHMENT)
                        .where(DatabaseHelper.ID_COLUMN + " = ?")
                        .whereArgs(attachmentId)
                        .build())
                .prepare()
                .asRxObservable()
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }
}

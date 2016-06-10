package io.blackbricks.todomanager.database.transforms;

import android.database.Cursor;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.model.Attachment;
import io.blackbricks.todomanager.utils.CursorWrap;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 11/04/16.
 */
public class CursorToAttachment implements Func1<Cursor, Attachment> {
    @Override
    public Attachment call(Cursor cursor) {
        CursorWrap cursorWrap = new CursorWrap(cursor);

        Integer id = cursorWrap.getInteger(DatabaseHelper.ID_COLUMN);
        String path = cursorWrap.getString(DatabaseHelper.ATTACHMENT_FILE_PATH_COLUMN);
        Integer taskId = cursorWrap.getInteger(DatabaseHelper.ATTACHMENT_TASK_ID_COLUMN);

        return new Attachment.Builder()
                .attachmentId(id)
                .path(path)
                .taskId(taskId)
                .build();
    }
}

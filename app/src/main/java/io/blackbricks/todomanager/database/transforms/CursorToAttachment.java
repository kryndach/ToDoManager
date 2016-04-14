package io.blackbricks.todomanager.database.transforms;

import android.database.Cursor;

import io.blackbricks.todomanager.model.Attachment;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 11/04/16.
 */
public class CursorToAttachment implements Func1<Cursor, Attachment> {
    @Override
    public Attachment call(Cursor cursor) {
        return null;
    }
}

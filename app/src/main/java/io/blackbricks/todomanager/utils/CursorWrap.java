package io.blackbricks.todomanager.utils;

import android.database.Cursor;

import java.util.Date;

/**
 * Created by yegorkryndach on 15/04/16.
 */
public class CursorWrap {

    Cursor cursor;

    public CursorWrap(Cursor cursor) {
        this.cursor = cursor;
    }

    public Integer getInteger(String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : cursor.getInt(index);
    }

    public String getString(String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : cursor.getString(index);
    }

    public Date getDate(String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return cursor.isNull(index) ? null : new Date(cursor.getInt(index));
    }
}

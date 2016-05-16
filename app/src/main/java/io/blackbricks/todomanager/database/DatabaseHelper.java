package io.blackbricks.todomanager.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yegorkryndach on 17/03/16.
 */

@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_manager_db.db";
    private static final int DATABASE_VERSION = 1;

    // Table Create Statements
    // Common
    public static final String ID_COLUMN = "id";

    // Group table create statement
    public static final String TABLE_GROUP = "groups";
    public static final String GROUP_NAME_COLUMN = "name";
    public static final String GROUP_TASK_COUNT_COLUMN = "task_count";
    public static final String GROUP_HOT_TASK_COUNT_COLUMN = "hot_task_count";
    private static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUP
            + "("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + GROUP_NAME_COLUMN + " TEXT NOT NULL, "
            // Counters
            + GROUP_TASK_COUNT_COLUMN + " INTEGER NOT NULL, "
            + GROUP_HOT_TASK_COUNT_COLUMN + " INTEGER NOT NULL"
            + ")";

    // Task table create statement
    public static final String TABLE_TASK = "tasks";
    public static final String TASK_DATE_ALARM_COLUMN = "date_alarm";
    public static final String TASK_DATE_CREATED_COLUMN = "date_created";
    public static final String TASK_DATE_DEADLINE_COLUMN = "date_deadline";
    public static final String TASK_DATE_STATUS_UPDATED_COLUMN = "date_status_updated";
    public static final String TASK_DESCRIPTION_COLUMN = "description";
    public static final String TASK_ICON_ID_COLUMN = "icon_id";
    public static final String TASK_STATUS_COLUMN = "status";
    public static final String TASK_TITLE_COLUMN = "title";
    public static final String TASK_GROUP_ID_COLUMN = "group_id";
    private static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK
            + "("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_DATE_ALARM_COLUMN + " INTEGER, "
            + TASK_DATE_CREATED_COLUMN + " INTEGER NOT NULL, "
            + TASK_DATE_DEADLINE_COLUMN + " INTEGER, "
            + TASK_DATE_STATUS_UPDATED_COLUMN + " INTEGER NOT NULL, "
            + TASK_DESCRIPTION_COLUMN + " TEXT, "
            + TASK_ICON_ID_COLUMN + " INTEGER, "
            + TASK_STATUS_COLUMN + " INTEGER NOT NULL, "
            + TASK_TITLE_COLUMN + " TEXT NOT NULL, "
            + TASK_GROUP_ID_COLUMN + " INTEGER, "
            + "FOREIGN KEY(" + TASK_GROUP_ID_COLUMN + ") "
            + "REFERENCES " + TABLE_GROUP + "(" + ID_COLUMN + ") ON DELETE SET NULL"
            + ")";

    // Attachment table create statement
    public static final String TABLE_ATTACHMENT = "attachments";
    public static final String ATTACHMENT_TYPE_COLUMN = "type";
    public static final String ATTACHMENT_FILE_PATH_COLUMN = "file_path";
    public static final String ATTACHMENT_TASK_ID_COLUMN = "task_id";
    private static final String CREATE_TABLE_ATTACHMENT = "CREATE TABLE " + TABLE_ATTACHMENT
            + "("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ATTACHMENT_TYPE_COLUMN + " INTEGER, "
            + ATTACHMENT_FILE_PATH_COLUMN + " TEXT NOT NULL, "
            + ATTACHMENT_TASK_ID_COLUMN + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + ATTACHMENT_TASK_ID_COLUMN + ") "
            + "REFERENCES " + TABLE_TASK + "(" + ID_COLUMN + ") ON DELETE CASCADE"
            + ")";

    @Inject
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GROUP);
        db.execSQL(CREATE_TABLE_TASK);
        db.execSQL(CREATE_TABLE_ATTACHMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DATABASE_VERSION < newVersion) {
            recreateDatabase(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        recreateDatabase(db);
    }

    private void recreateDatabase(SQLiteDatabase db) {
        // TODO remove this hard reset DB in future
        db.execSQL("PRAGMA writable_schema = 1;" +
                "delete from sqlite_master where type in ('table', 'index', 'trigger')" +
                "PRAGMA writable_schema = 0;");

        // Drop tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTACHMENT);

        onCreate(db);
    }

}

package io.blackbricks.todomanager.database;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.blackbricks.todomanager.model.Attachment;
import io.blackbricks.todomanager.model.AttachmentStorIOSQLiteDeleteResolver;
import io.blackbricks.todomanager.model.AttachmentStorIOSQLiteGetResolver;
import io.blackbricks.todomanager.model.AttachmentStorIOSQLitePutResolver;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.GroupStorIOSQLiteDeleteResolver;
import io.blackbricks.todomanager.model.GroupStorIOSQLiteGetResolver;
import io.blackbricks.todomanager.model.GroupStorIOSQLitePutResolver;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskStorIOSQLiteDeleteResolver;
import io.blackbricks.todomanager.model.TaskStorIOSQLiteGetResolver;
import io.blackbricks.todomanager.model.TaskStorIOSQLitePutResolver;
import rx.schedulers.Schedulers;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    StorIOSQLite storIOSQLiteProvider(DatabaseHelper databaseHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(databaseHelper)
                .addTypeMapping(Attachment.class, SQLiteTypeMapping.<Attachment>builder()
                        .putResolver(new AttachmentStorIOSQLitePutResolver())
                        .getResolver(new AttachmentStorIOSQLiteGetResolver())
                        .deleteResolver(new AttachmentStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(Group.class, SQLiteTypeMapping.<Group>builder()
                        .putResolver(new GroupStorIOSQLitePutResolver())
                        .getResolver(new GroupStorIOSQLiteGetResolver())
                        .deleteResolver(new GroupStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(Task.class, SQLiteTypeMapping.<Task>builder()
                        .putResolver(new TaskStorIOSQLitePutResolver())
                        .getResolver(new TaskStorIOSQLiteGetResolver())
                        .deleteResolver(new TaskStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }
}

package io.blackbricks.todomanager.database;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    BriteDatabase briteDatabaseProvider(DatabaseHelper databaseHelper) {
        SqlBrite sqlBrite = SqlBrite.create();
        return sqlBrite.wrapDatabaseHelper(databaseHelper, Schedulers.io());
    }

    @Provides
    @Singleton
    StorIOSQLite storIOSQLiteProvider(DatabaseHelper databaseHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(databaseHelper)
                .build();
    }
}

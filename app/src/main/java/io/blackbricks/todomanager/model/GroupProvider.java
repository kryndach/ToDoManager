package io.blackbricks.todomanager.model;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.database.transforms.CursorToGroup;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@Singleton
public class GroupProvider {

    @Inject
    BriteDatabase database;

    @Inject
    public GroupProvider() {
    }

    public Observable<List<Group>> getGroups(){
        return database.createQuery(DatabaseHelper.TABLE_GROUP, "SELECT * FROM ", DatabaseHelper.TABLE_GROUP)
                .mapToList(new CursorToGroup())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Group> getGroup(String groupId) {
        String condition = " WHERE " + DatabaseHelper.ID_COLUMN + " = " + groupId;
        return database.createQuery(DatabaseHelper.TABLE_GROUP,
                "SELECT * FROM ", DatabaseHelper.TABLE_GROUP, condition)
                .mapToOne(new CursorToGroup())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

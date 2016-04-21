package io.blackbricks.todomanager.model;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.database.transforms.CursorToGroup;
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

    public rx.Observable<List<Group>> getGroups(){
        return database.createQuery("groups", "SELECT * FROM ", DatabaseHelper.TABLE_GROUP)
                .mapToList(new CursorToGroup())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

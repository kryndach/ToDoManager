package io.blackbricks.todomanager.model;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.database.transforms.CursorToGroup;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@Singleton
public class GroupProvider {

    @Inject
    BriteDatabase database;

    @Inject
    StorIOSQLite storIO;

    @Inject
    public GroupProvider() {
    }

    public Observable<List<Group>> getGroups(){
        return storIO
                .get()
                .listOfObjects(Group.class)
                .withQuery(Query.builder()
                        .table(DatabaseHelper.TABLE_GROUP)
                        .build())
                .prepare()
                .asRxObservable()
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Group> getGroup(Integer groupId) {
        return storIO
                .get()
                .object(Group.class)
                .withQuery(Query.builder()
                        .table(DatabaseHelper.TABLE_GROUP)
                        .where(DatabaseHelper.ID_COLUMN + " = ?")
                        .whereArgs(groupId)
                        .build())
                .prepare()
                .asRxObservable()
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }
}

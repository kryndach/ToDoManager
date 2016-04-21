package io.blackbricks.todomanager.model;

import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@Singleton
public class AttachmentProvider {

    @Inject
    BriteDatabase database;

    @Inject
    public AttachmentProvider() {
    }
}

package io.blackbricks.todomanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by yegorkryndach on 27/04/16.
 */
@Singleton
public class IconProvider {

    @Inject
    public IconProvider() {
    }

    public Observable<List<Integer>> getIcons(){
        List<Integer> icons = new ArrayList<>();
        return Observable.just(icons);
    }
}

package io.blackbricks.todomanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.R;
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
        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.folder_outline_black);
        icons.add(R.drawable.bell_black);
        icons.add(R.drawable.camera_black);
        icons.add(R.drawable.image_black);
        icons.add(R.drawable.timer_black);

        return Observable.just((List<Integer>) icons);
    }
}

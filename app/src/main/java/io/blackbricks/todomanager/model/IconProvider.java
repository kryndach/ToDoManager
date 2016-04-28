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
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);
        icons.add(R.drawable.ic_assignment_turned_in_black_24dp);

        return Observable.just((List<Integer>) icons);
    }
}

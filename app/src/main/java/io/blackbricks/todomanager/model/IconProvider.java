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
        icons.add(R.drawable.account);
        icons.add(R.drawable.airplane);
        icons.add(R.drawable.amazon_clouddrive);
        icons.add(R.drawable.ambulance);
        icons.add(R.drawable.anchor);
        icons.add(R.drawable.android);
        icons.add(R.drawable.android_studio);
        icons.add(R.drawable.appnet);
        icons.add(R.drawable.at);
        icons.add(R.drawable.attachment);
        icons.add(R.drawable.baby);
        icons.add(R.drawable.basecamp);
        icons.add(R.drawable.battery_plus);
        icons.add(R.drawable.beach);
        icons.add(R.drawable.beer);
        icons.add(R.drawable.bike);
        icons.add(R.drawable.binoculars);
        icons.add(R.drawable.biohazard);
        icons.add(R.drawable.bone);
        icons.add(R.drawable.bookmark);
        icons.add(R.drawable.bowl);
        icons.add(R.drawable.brightness_2);
        icons.add(R.drawable.brightness_5);
        icons.add(R.drawable.brush);
        icons.add(R.drawable.bug);
        icons.add(R.drawable.bus);
        icons.add(R.drawable.cake);
        icons.add(R.drawable.cake_variant);
        icons.add(R.drawable.calendar_text);
        icons.add(R.drawable.camcorder);
        icons.add(R.drawable.cards);
        icons.add(R.drawable.castle);
        icons.add(R.drawable.chart_line);
        icons.add(R.drawable.church);
        icons.add(R.drawable.code_braces);
        icons.add(R.drawable.coffee);
        icons.add(R.drawable.cup);
        icons.add(R.drawable.delete);
        icons.add(R.drawable.fish);
        icons.add(R.drawable.flask_empty);
        icons.add(R.drawable.food);
        icons.add(R.drawable.football);
        icons.add(R.drawable.gamepad);
        icons.add(R.drawable.gavel);
        icons.add(R.drawable.gender_female);
        icons.add(R.drawable.gender_male);
        icons.add(R.drawable.guitar_electric);
        icons.add(R.drawable.hanger);
        icons.add(R.drawable.home_variant);
        icons.add(R.drawable.leaf);
        icons.add(R.drawable.music_note);
        icons.add(R.drawable.silverware_variant);
        icons.add(R.drawable.verified);

        return Observable.just((List<Integer>) icons);
    }
}

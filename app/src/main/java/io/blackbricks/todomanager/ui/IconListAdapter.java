package io.blackbricks.todomanager.ui;

import android.widget.ImageView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import io.blackbricks.todomanager.R;

/**
 * Created by yegorkryndach on 26/04/16.
 */
public class IconListAdapter extends SupportAnnotatedAdapter {

    @ViewType(layout = R.layout.list_icon_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.image, name = "image", type = ImageView.class),
            })
    public final int iconItem = 0;

}

package io.blackbricks.todomanager.ui;

import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import io.blackbricks.todomanager.R;

/**
 * Created by yegorkryndach on 26/04/16.
 */
public class GroupListAdapter extends SupportAnnotatedAdapter {

    @ViewType(layout = R.layout.list_group_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.title, name = "title", type = TextView.class),
            })
    public final int groupItem = 0;

}

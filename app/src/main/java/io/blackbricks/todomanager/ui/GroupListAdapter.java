package io.blackbricks.todomanager.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import java.util.ArrayList;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 26/04/16.
 */
public class GroupListAdapter extends SupportAnnotatedAdapter implements GroupListAdapterBinder {

    @ViewType(layout = R.layout.list_group_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.title, name = "title", type = TextView.class),
            })
    public final int groupItem = 0;

    ArrayList<Group> groupList;

    public GroupListAdapter(Context context) {
        super(context);
    }

    public int getGroupItem() {
        return groupItem;
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    @Override
    public void initViewHolder(GroupListAdapterHolders.GroupItemViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(GroupListAdapterHolders.GroupItemViewHolder vh, int position) {

    }
}

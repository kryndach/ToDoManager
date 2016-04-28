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

    public interface GroupClickListener {
        public void onGroupClicked(Group group);
    }

    @ViewType(layout = R.layout.list_group_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.title, name = "title", type = TextView.class),
            })
    public final int groupItem = 0;

    ArrayList<Group> groupList;
    private GroupClickListener groupClickListener;

    public GroupListAdapter(Context context, ArrayList<Group> groupList,
                            GroupClickListener groupClickListener) {
        super(context);
        this.groupList = groupList;
        this.groupClickListener = groupClickListener;
    }

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<Group> groupList) {
        this.groupList = groupList;
    }

    @Override
    public int getItemCount() {
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public void initViewHolder(GroupListAdapterHolders.GroupItemViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(GroupListAdapterHolders.GroupItemViewHolder vh, int position) {
        final Group group = groupList.get(position);
        vh.title.setText(group.getName());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupClickListener.onGroupClicked(group);
            }
        });
    }
}

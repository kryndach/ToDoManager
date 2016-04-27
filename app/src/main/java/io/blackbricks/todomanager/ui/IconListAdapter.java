package io.blackbricks.todomanager.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import java.util.ArrayList;

import io.blackbricks.todomanager.R;

/**
 * Created by yegorkryndach on 26/04/16.
 */
public class IconListAdapter extends SupportAnnotatedAdapter implements IconListAdapterBinder {

    @ViewType(layout = R.layout.list_icon_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.image, name = "image", type = ImageView.class),
            })
    public final int iconItem = 0;

    ArrayList<Integer> iconList;

    public IconListAdapter(Context context) {
        super(context);
    }

    public int getIconItem() {
        return iconItem;
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    @Override
    public void initViewHolder(IconListAdapterHolders.IconItemViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(IconListAdapterHolders.IconItemViewHolder vh, int position) {

    }
}

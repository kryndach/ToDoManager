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

    public interface IconClickListener {
        public void onIconClicked(Integer iconId);
    }

    @ViewType(layout = R.layout.list_icon_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.image, name = "image", type = ImageView.class),
            })
    public final int iconItem = 0;

    ArrayList<Integer> iconList;
    private IconClickListener iconClickListener;

    public IconListAdapter(Context context, ArrayList<Integer> iconList,
                           IconClickListener iconClickListener) {
        super(context);
        this.iconList = iconList;
        this.iconClickListener = iconClickListener;
    }

    public ArrayList<Integer> getIconList() {
        return iconList;
    }

    public void setIconList(ArrayList<Integer> iconList) {
        this.iconList = iconList;
    }

    @Override
    public int getItemCount() {
        return iconList == null ? 0 : iconList.size();
    }

    @Override
    public void initViewHolder(IconListAdapterHolders.IconItemViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(IconListAdapterHolders.IconItemViewHolder vh, int position) {
        final Integer iconId = iconList.get(position);
        vh.image.setImageResource(iconId);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconClickListener.onIconClicked(iconId);
            }
        });
    }
}

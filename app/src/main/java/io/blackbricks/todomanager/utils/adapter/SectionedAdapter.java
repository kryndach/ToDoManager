package io.blackbricks.todomanager.utils.adapter;

import android.content.Context;

import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

/**
 * Created by yegorkryndach on 26/05/16.
 */
public abstract class SectionedAdapter extends SupportAnnotatedAdapter {

    protected final int ITEM_SECTION_HEADER = -1;

    public SectionedAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (int section = 0; section < getSectionCount(); section++) {
            itemCount += getItemCount(section);
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int section = getSection(position);
        return getItemViewTypeBySection(section);
    }

    abstract int getItemCount(int section);

    abstract int getSectionCount();

    abstract int getItemViewTypeBySection(int section);

    protected int getPositionInSection(int position) {
        return 0;
    }

    private int getSection(int position) {
        return 0;
    }
}

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
            if(supportHeader(section)) {
                itemCount++;
            }
            itemCount += getSectionItemCount(section);
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int section = getSection(position);
        if(supportHeader(section)) {
            int positionInSection = getPositionInSection(position);
            if(positionInSection == -1) {
                return ITEM_SECTION_HEADER;
            }
        }
        return getItemViewTypeBySection(section);
    }

    protected abstract int getSectionItemCount(int section);

    protected abstract boolean supportHeader(int section);

    protected abstract int getSectionCount();

    protected abstract int getItemViewTypeBySection(int section);

    protected int getPositionInSection(int position) {
        int itemCount = 0;
        int section;
        for (section = 0; section < getSectionCount(); section++) {
            int curItemCount = getSectionItemCount(section) + (supportHeader(section) ? 1 : 0);
            itemCount += curItemCount;
            if(itemCount > position)
                return position - (itemCount - curItemCount) - (supportHeader(section) ? 1 : 0);
        }
        return 0;
    }

    protected int getPositionStartSection(int section) {
        int itemCount = 0;
        for (int curSection = 0; curSection < section; curSection++) {
            int curItemCount = getSectionItemCount(curSection) + (supportHeader(curSection) ? 1 : 0);
            itemCount += curItemCount;
        }
        return itemCount;
    }

    private int getSection(int position) {
        int itemCount = 0;
        int section;
        for (section = 0; section < getSectionCount(); section++) {
            int curItemCount = getSectionItemCount(section) + (supportHeader(section) ? 1 : 0);
            itemCount += curItemCount;
            if(itemCount > position)
                return section;
        }
        return section;
    }
}

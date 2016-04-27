package io.blackbricks.todomanager.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yegorkryndach on 27/04/16.
 */
public class GridInsetDecoration extends RecyclerView.ItemDecoration {

    private int mInsets;

    public GridInsetDecoration(Context context, @DimenRes int insets) {
        mInsets = context.getResources().getDimensionPixelSize(insets);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //We can supply forced insets for each item view here in the Rect
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}

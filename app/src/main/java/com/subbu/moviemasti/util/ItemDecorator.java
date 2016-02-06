package com.subbu.moviemasti.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by subrahmanyam on 06-02-2016, 05:05 PM.
 */
public class ItemDecorator extends RecyclerView.ItemDecoration {
    private int horizantal;
    private int vertical;

    public ItemDecorator(Context context, AttributeSet attrs) {
        final TypedArray a = context
                .obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
        a.recycle();
    }

    public ItemDecorator(int horizantal, int vertical) {
        this.horizantal = horizantal;
        this.vertical = vertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = horizantal;
        outRect.right = horizantal;
        outRect.top = vertical;
        outRect.bottom = vertical;
    }
}

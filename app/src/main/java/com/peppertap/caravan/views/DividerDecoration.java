package com.peppertap.caravan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Shine on 20/11/15.
 */
public class DividerDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public DividerDecoration(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public DividerDecoration(Drawable divider) {
        mDivider = divider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mDivider == null) return;
        if (isDividerNeeded(parent.getChildViewHolder(view)) ) return;
        outRect.bottom = mDivider.getIntrinsicHeight();

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            super.onDrawOver(c, parent);
            return;
        }

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (isDividerNeeded(parent.getChildViewHolder(child))) {
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int size = mDivider.getIntrinsicHeight();
                final int top = child.getTop() - params.topMargin;
                int atop = child.getBottom();
                //  final int bottom = top + size;
                int abottom = child.getBottom() + size;
                mDivider.setBounds(left, atop, right, abottom);
                mDivider.draw(c);
            }
        }

    }

    private boolean isDividerNeeded(RecyclerView.ViewHolder childViewHolder) {
        return true;
    }


}
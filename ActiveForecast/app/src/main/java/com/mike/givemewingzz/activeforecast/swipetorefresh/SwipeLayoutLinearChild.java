package com.mike.givemewingzz.activeforecast.swipetorefresh;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SwipeLayoutLinearChild extends LinearLayout {
    private View scrollableChild;

    public SwipeLayoutLinearChild(Context context) {
        super(context);
    }

    public SwipeLayoutLinearChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeLayoutLinearChild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollableChild(View view){
        scrollableChild = view;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return canChildScrollUp();
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {
        return scrollableChild != null && ViewCompat.canScrollVertically(scrollableChild, -1);
    }
}

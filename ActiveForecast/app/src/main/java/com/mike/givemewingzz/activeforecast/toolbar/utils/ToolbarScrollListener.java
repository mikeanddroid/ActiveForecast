package com.mike.givemewingzz.activeforecast.toolbar.utils;

import android.support.v7.widget.RecyclerView;

public class ToolbarScrollListener extends RecyclerView.OnScrollListener {
    public static interface ToolbarScrollHandler {

        public abstract void onHideToolbar();

        public abstract void onShowToolbar();
    }


    private ToolbarScrollHandler callback;
    int scrollAmount;
    private int scrollThreshold;

    public ToolbarScrollListener(int i, ToolbarScrollHandler toolbarscrollhandler) {
        scrollAmount = 0;
        scrollThreshold = i;
        callback = toolbarscrollhandler;
    }

    public void onScrolled(RecyclerView recyclerview, int i, int j) {
        super.onScrolled(recyclerview, i, j);
        scrollAmount = scrollAmount + j;
        if ((double) scrollAmount >= (double) (float) scrollThreshold + (double) (float) scrollThreshold * 0.20000000000000001D) {
            callback.onHideToolbar();
        } else if (scrollAmount < scrollThreshold || !recyclerview.canScrollVertically(-1)) {
            callback.onShowToolbar();
            return;
        }
    }

    public void reset() {
        scrollAmount = 0;
    }
}

package com.mike.givemewingzz.activeforecast.navigationframework.toolbar;

import android.support.v7.widget.RecyclerView;

public class ToolbarScrollListener extends RecyclerView.OnScrollListener {

    private int scrollThreshold;
    private ToolbarScrollHandler callback;
    int scrollAmount = 0;

    public ToolbarScrollListener(int scrollThreshold, ToolbarScrollHandler callback) {
        this.scrollThreshold = scrollThreshold;
        this.callback = callback;
    }

    public void reset() {
        scrollAmount = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        scrollAmount += dy;

        // The hide threshold is 20% lower than the show to prevent bouncing toolbar syndrome //
        if (scrollAmount >= (((float) scrollThreshold) + ((float) scrollThreshold) * .2)) {
            callback.onHideToolbar();
        } else if (scrollAmount < scrollThreshold || !recyclerView.canScrollVertically(-1)) {
            callback.onShowToolbar();
        }
    }

    public interface ToolbarScrollHandler {
        void onHideToolbar();

        void onShowToolbar();
    }
}
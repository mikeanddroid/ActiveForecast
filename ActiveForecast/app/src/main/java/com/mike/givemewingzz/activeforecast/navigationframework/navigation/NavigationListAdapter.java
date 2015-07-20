package com.mike.givemewingzz.activeforecast.navigationframework.navigation;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mike.givemewingzz.activeforecast.R;

import java.util.List;

public class NavigationListAdapter extends RecyclerView.Adapter<NavigationListAdapter.CoreViewHolder> {

    private int mSelectedPosition;
    private int mTouchedPosition = -1;
    private List<NavigationModel> navigationList;
    private NavigationListener navigationListener;

    private boolean locked = false;

    public NavigationListAdapter(List<NavigationModel> navigationList) {
        this.navigationList = navigationList;
    }

    public void setNavigationListener(NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    @Override
    public CoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup defaultViewGroup = (ViewGroup) inflater.inflate(
                R.layout.navigation_row, viewGroup, false);

        DefaultListitemsHolder defaultListitemsHolder = new DefaultListitemsHolder(
                defaultViewGroup);

        return defaultListitemsHolder;
    }

    @Override
    public void onBindViewHolder(CoreViewHolder coreViewHolder,
                                 final int position) {

        DefaultListitemsHolder defaultListitemsHolder = (DefaultListitemsHolder) coreViewHolder;

        // Todo : Set the values for the nav items

        defaultListitemsHolder.labelTextView.setText(navigationList.get(
                position).getlabel());

        defaultListitemsHolder.labelTextView
                .setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                touchPosition(position);
                                return false;
                            case MotionEvent.ACTION_CANCEL:
                                touchPosition(-1);
                                return false;
                            case MotionEvent.ACTION_MOVE:
                                return false;
                            case MotionEvent.ACTION_UP:
                                return false;
                        }
                        return true;
                    }
                });

        defaultListitemsHolder.labelTextView
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (navigationListener != null) {
                            navigationListener
                                    .onNavigationDrawerItemSelected(position);
                        }

                    }
                });

        if (mSelectedPosition == position || mTouchedPosition == position) {
            defaultListitemsHolder.labelTextView
                    .setBackgroundColor(defaultListitemsHolder.labelTextView
                            .getContext().getResources().getColor(R.color.grey));
            defaultListitemsHolder.labelTextView
                    .setCompoundDrawablesWithIntrinsicBounds(navigationList
                                    .get(position).getNavLabelUnSelected(), null, null,
                            null);
        } else {
            defaultListitemsHolder.labelTextView
                    .setBackgroundColor(Color.TRANSPARENT);
            defaultListitemsHolder.labelTextView
                    .setCompoundDrawablesWithIntrinsicBounds(navigationList
                                    .get(position).getNavLabelSelected(), null, null,
                            null);
        }

    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition);
        if (position >= 0)
            notifyItemChanged(position);
    }

    public void selectPosition(int position) {
        mSelectedPosition = position;
        mTouchedPosition = -1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return navigationList != null ? navigationList.size() : 0;
    }

    public static class DefaultListitemsHolder extends CoreViewHolder {

        public TextView labelTextView;

        public DefaultListitemsHolder(View view) {
            super(view);
            labelTextView = (TextView) view.findViewById(R.id.label_name);
        }

    }

    public static class CoreViewHolder extends RecyclerView.ViewHolder {

        public CoreViewHolder(View view) {
            super(view);
        }

    }

}

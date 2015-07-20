package com.mike.givemewingzz.activeforecast.navigationdrawer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// Referenced classes of package com.example.givemewingz.activeforecast.mike.navigation:
//            NavigationModel

public class NavigationListAdapter extends android.support.v7.widget.RecyclerView.Adapter
{
    public static class CoreViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder
    {

        public CoreViewHolder(View view)
        {
            super(view);
        }
    }

    public static class DefaultListitemsHolder extends CoreViewHolder
    {

        public TextView labelTextView;

        public DefaultListitemsHolder(View view)
        {
            super(view);
           // labelTextView = (TextView)view.findViewById(0x7f0c006b);
        }
    }


    private boolean locked;
    private int mSelectedPosition;
    private int mTouchedPosition;
    private List navigationList;
    private NavigationListener navigationListener;

    public NavigationListAdapter(List list)
    {
        mTouchedPosition = -1;
        locked = false;
        navigationList = list;
    }

    private void touchPosition(int i)
    {
        int j = mTouchedPosition;
        mTouchedPosition = i;
        if (j >= 0)
        {
            notifyItemChanged(j);
        }
        if (i >= 0)
        {
            notifyItemChanged(i);
        }
    }

    public int getItemCount()
    {
        if (navigationList != null)
        {
            return navigationList.size();
        } else
        {
            return 0;
        }
    }

    public void lock()
    {
        locked = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewholder, int i)
    {
        onBindViewHolder((CoreViewHolder)viewholder, i);
    }

    public void onBindViewHolder(CoreViewHolder coreviewholder, final int position)
    {
//        coreviewholder = (DefaultListitemsHolder)coreviewholder;
//        //coreviewholder.labelTextView.setText(((NavigationModel)navigationList.get(position)).getlabel());
//        //coreviewholder.labelTextView.setOnTouchListener(new View.OnTouchListener() {
//
//           // int i = 0;
//
//            public boolean onTouch(View view, MotionEvent motionevent)
//            {
//                boolean flag = false;
//                switch (motionevent.getAction())
//                {
//                default:
//                    flag = true;
//                    // fall through
//
//                case 1: // '\001'
//                case 2: // '\002'
//                    return flag;
//
//                case 0: // '\0'
//                    touchPosition(position);
//                    return false;
//
//                case 3: // '\003'
//                    touchPosition(-1);
//                    break;
//                }
//                return false;
//            }
//
//
//            {
//
//                position = i;
//                super();
//            }
//        });
//        ((DefaultListitemsHolder) (coreviewholder)).labelTextView.setOnClickListener(new View.OnClickListener() {
//
//            final NavigationListAdapter this$0;
//            final int position;
//
//            public void onClick(View view)
//            {
//                if (navigationListener != null)
//                {
//                    navigationListener.onNavigationDrawerItemSelected(position);
//                }
//            }
//
//
//            {
//                this$0 = NavigationListAdapter.this;
//                position = i;
//                super();
//            }
//        });
//        if (mSelectedPosition == position || mTouchedPosition == position)
//        {
//            ((DefaultListitemsHolder) (coreviewholder)).labelTextView.setBackgroundColor(((DefaultListitemsHolder) (coreviewholder)).labelTextView.getContext().getResources().getColor(0x7f0b0021));
//            ((DefaultListitemsHolder) (coreviewholder)).labelTextView.setCompoundDrawablesWithIntrinsicBounds(((NavigationModel)navigationList.get(position)).getNavLabelUnSelected(), null, null, null);
//            return;
//        } else
//        {
//            ((DefaultListitemsHolder) (coreviewholder)).labelTextView.setBackgroundColor(0);
//            ((DefaultListitemsHolder) (coreviewholder)).labelTextView.setCompoundDrawablesWithIntrinsicBounds(((NavigationModel)navigationList.get(position)).getNavLabelSelected(), null, null, null);
//            return;
//        }
    }

//    public volatile android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewgroup, int i)
//    {
//        return onCreateViewHolder(viewgroup, i);
//    }
//
//    public CoreViewHolder onCreateViewHolder(ViewGroup viewgroup, int i)
//    {
//        return new DefaultListitemsHolder((ViewGroup)LayoutInflater.from(viewgroup.getContext()).inflate(0x7f04001e, viewgroup, false));
//    }

    public void selectPosition(int i)
    {
        mSelectedPosition = i;
        mTouchedPosition = -1;
        notifyDataSetChanged();
    }

    public void setNavigationListener(NavigationListener navigationlistener)
    {
        navigationListener = navigationlistener;
    }

    public void unlock()
    {
        locked = false;
    }


}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.mike.givemewingzz.activeforecast.navigationdrawer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mike.givemewingzz.activeforecast.UI.TestClassFourActivity;
import com.mike.givemewingzz.activeforecast.UI.TestClassOneActivity;
import com.mike.givemewingzz.activeforecast.UI.TestClassThreeActivity;
import com.mike.givemewingzz.activeforecast.UI.TestClassTwoActivity;
import com.mike.givemewingzz.activeforecast.baseclasses.CoreActivity;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.example.givemewingz.activeforecast.mike.navigation:
//            NavigationHandler, NavigationModel, NavigationListAdapter

public class CoreNavigationActivity extends CoreActivity
    implements NavigationListener, NavigationHandler
{
    public enum DrawerNavState {
        TOGGLE, BACK, NONE
    }

    public static class ToggleNavigationAction
    {

        public ActionBarDrawerToggle actionBarDrawerToggle;
        public DrawerNavState drawerNavState;

        public ToggleNavigationAction(ActionBarDrawerToggle actionbardrawertoggle, DrawerNavState drawernavstate)
        {
            actionBarDrawerToggle = actionbardrawertoggle;
            drawerNavState = drawernavstate;
        }
    }


    private static final String SELECTED_NAVIGATION_DRAWER_POSITION = "SELECTED_NAVIGATION_DRAWER_POSITION";
    private static final String TAG = CoreNavigationActivity.class.getSimpleName();
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private RecyclerView drawerList;
    private int mCurrentSelectedPosition;
    private int mNewSelectedPosition;
    private NavigationListAdapter navigationListAdapter;

    public CoreNavigationActivity()
    {
        mCurrentSelectedPosition = 0;
        mNewSelectedPosition = 0;
    }

    private void launchActivity()
    {

        if (mNewSelectedPosition != mCurrentSelectedPosition) {
            Intent intent = null;

            switch (mNewSelectedPosition) {

                case 0:intent = new Intent(this, TestClassOneActivity.class);break;

                case 1: intent = new Intent(this, TestClassTwoActivity.class);break;

                case 2: intent = new Intent(this, TestClassThreeActivity.class);break;

                case 3: intent = new Intent(this, TestClassFourActivity.class);break;

            }

            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        }

    }

    private List setupNavigationList()
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(new NavigationModel("Active Weather", getResources().getDrawable(0x7f020007), getResources().getDrawable(0x7f020008)));
        arraylist.add(new NavigationModel("Hourly Data", getResources().getDrawable(0x7f020007), getResources().getDrawable(0x7f020008)));
        arraylist.add(new NavigationModel("Forecast", getResources().getDrawable(0x7f020007), getResources().getDrawable(0x7f020008)));
        arraylist.add(new NavigationModel("Settings", getResources().getDrawable(0x7f020007), getResources().getDrawable(0x7f020008)));
        return arraylist;
    }

    public void closeDrawer()
    {
        drawerLayout.closeDrawer(drawerList);
    }

    public boolean isDrawerOpen()
    {
label0:
        {
            boolean flag2 = false;
            boolean flag;
            boolean flag1;
            if (drawerLayout != null && drawerLayout.isDrawerOpen(drawerList))
            {
                flag = true;
            } else
            {
                flag = false;
            }
            if (flag)
            {
                flag1 = flag2;
                if (!drawerLayout.isDrawerVisible(drawerLayout))
                {
                    break label0;
                }
            }
            flag1 = flag2;
            if (flag)
            {
                flag1 = true;
            }
        }
        return flag1;
    }

    public void lockDrawer()
    {
        if (drawerLayout != null)
        {
            drawerLayout.setDrawerLockMode(1);
        }
    }

    public void onBackPressed()
    {
        if (isDrawerOpen())
        {
            closeDrawer();
            return;
        } else
        {
            super.onBackPressed();
            return;
        }
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        actionBarDrawerToggle.onConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f040019);
        drawerLayout = (DrawerLayout)findViewById(0x7f0c0050);
        bundle = new LinearLayoutManager(this);
        bundle.setOrientation(1);
        drawerList = (RecyclerView)findViewById(0x7f0c0053);
        drawerList.setLayoutManager(bundle);
        drawerList.setHasFixedSize(true);
        navigationListAdapter = new NavigationListAdapter(setupNavigationList());
        navigationListAdapter.setNavigationListener(this);
        drawerList.setAdapter(navigationListAdapter);
    }

    public void onNavigationDrawerItemSelected(int i)
    {
        mNewSelectedPosition = i;
        if (mNewSelectedPosition == mCurrentSelectedPosition)
        {
            selectedNavItemClicked();
        }
        lockDrawer();
        closeDrawer();
    }

    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putInt("SELECTED_NAVIGATION_DRAWER_POSITION", mCurrentSelectedPosition);
    }

    public void selectedNavItemClicked()
    {
    }

    public void setBackArrowDrawable(int i)
    {
        actionBarDrawerToggle.setHomeAsUpIndicator(i);
    }

    public void setDrawerToggle(ToggleNavigationAction togglenavigationaction)
    {
        actionBarDrawerToggle = togglenavigationaction.actionBarDrawerToggle;
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        unlockDrawer();
    }

    public void setSelectedNavigationIndex(int i)
    {
        ((NavigationListAdapter)drawerList.getAdapter()).selectPosition(i);
        mCurrentSelectedPosition = i;
        mNewSelectedPosition = i;
    }

    public ToggleNavigationAction setupNavigationDrawer(Toolbar toolbar, DrawerNavState drawernavstate, int i)
    {
        return setupToobar(toolbar, drawernavstate, i);
    }

    public ToggleNavigationAction setupToobar(Toolbar toolbar, DrawerNavState drawernavstate, int i)
    {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0x7f060015, 0x7f060014) {

            final CoreNavigationActivity this$0;

            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                launchActivity();
                navigationListAdapter.unlock();
                unlockDrawer();
            }

            public void onDrawerOpened(View view)
            {
                super.onDrawerOpened(view);
            }

            
            {
                this$0 = CoreNavigationActivity.this;
                super(activity, drawerlayout, toolbar, i, j);
            }
        };
        if (drawernavstate == DrawerNavState.BACK)
        {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            actionBarDrawerToggle.setHomeAsUpIndicator(i);
            actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {

                final CoreNavigationActivity this$0;

                public void onClick(View view)
                {
                    getSupportFragmentManager().popBackStack();
                }

            
            {
                this$0 = CoreNavigationActivity.this;
                super();
            }
            });
        } else
        if (drawernavstate == DrawerNavState.TOGGLE)
        {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        } else
        {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        }
        drawerLayout.post(new Runnable() {

            final CoreNavigationActivity this$0;

            public void run()
            {
                actionBarDrawerToggle.syncState();
            }

            
            {
                this$0 = CoreNavigationActivity.this;
                super();
            }
        });
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        return new ToggleNavigationAction(actionBarDrawerToggle, drawernavstate);
    }

    public void unlockDrawer()
    {
        if (drawerLayout != null)
        {
            drawerLayout.setDrawerLockMode(0);
        }
    }




}

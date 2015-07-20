package com.mike.givemewingzz.activeforecast.navigationdrawer;

import android.support.v7.widget.Toolbar;

public interface NavigationHandler {

    void lockDrawer();

    void setBackArrowDrawable(int i);

    void setDrawerToggle(CoreNavigationActivity.ToggleNavigationAction togglenavigationaction);

    CoreNavigationActivity.ToggleNavigationAction setupNavigationDrawer(Toolbar toolbar, CoreNavigationActivity.DrawerNavState drawernavstate, int i);

    void unlockDrawer();
}

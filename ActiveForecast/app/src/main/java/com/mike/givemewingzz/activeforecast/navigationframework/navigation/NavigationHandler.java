package com.mike.givemewingzz.activeforecast.navigationframework.navigation;

import android.support.v7.widget.Toolbar;

public interface NavigationHandler {

    CoreNavigationActivity.ToggleNavigationAction setupNavigationDrawer(Toolbar toolbar, CoreNavigationActivity.DrawerNavState drawerNavState, int backArrowDrawable);

    void setBackArrowDrawable(int drawableID);

    void setDrawerToggle(CoreNavigationActivity.ToggleNavigationAction toggleNavigationAction);

    void lockDrawer();

    void unlockDrawer();
}

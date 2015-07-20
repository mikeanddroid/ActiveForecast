package com.mike.givemewingzz.activeforecast.toolbar.utils;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;

import com.mike.givemewingzz.activeforecast.navigationdrawer.CoreNavigationActivity;

public class ToolbarOptions {

    private boolean attachToNavDrawer;
    private CoreNavigationActivity.DrawerNavState drawerNavState;
    private int menuID;
    private int navDrawableID;
    private int navDrawerBackDrawableID;
    private String title;
    private Toolbar toolbar;
    private Drawable toolbarBackground;

    public ToolbarOptions(Toolbar toolbar1, String s, Drawable drawable, int i, boolean flag, int j, int k,
                          CoreNavigationActivity.DrawerNavState drawernavstate) {
        toolbar = toolbar1;
        title = s;
        toolbarBackground = drawable;
        navDrawableID = i;
        attachToNavDrawer = flag;
        navDrawerBackDrawableID = j;
        menuID = k;
        drawerNavState = drawernavstate;
    }

    public boolean attachToNavDrawer() {
        return attachToNavDrawer;
    }

    public CoreNavigationActivity.DrawerNavState getDrawerNavState() {
        return drawerNavState;
    }

    public int getMenuID() {
        return menuID;
    }

    public int getNavDrawableID() {
        return navDrawableID;
    }

    public int getNavDrawerBackDrawableID() {
        return navDrawerBackDrawableID;
    }

    public String getTitle() {
        return title;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public Drawable getToolbarBackground() {
        return toolbarBackground;
    }
}

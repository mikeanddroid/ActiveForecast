package com.mike.givemewingzz.activeforecast.toolbar.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;

import com.mike.givemewingzz.activeforecast.exceptions.ToolbarInteractionException;
import com.mike.givemewingzz.activeforecast.navigationdrawer.CoreNavigationActivity;


public class ToolbarOptionsBuilder {

    private boolean attachToNavDrawer;
    private int backDrawableID;
    private CoreNavigationActivity.DrawerNavState drawerNavState;
    private int menuID;
    private int navDrawerBackDrawableID;
    private String title;
    private Toolbar toolbar;
    private Drawable toolbarBackground;

    public ToolbarOptionsBuilder() {
        title = "";
        backDrawableID = -1;
        menuID = -1;
        attachToNavDrawer = false;
        navDrawerBackDrawableID = 0x7f02003b;
        drawerNavState = CoreNavigationActivity.DrawerNavState.NONE;
    }

    public ToolbarOptions build(Context context) throws ToolbarInteractionException {
        if (toolbar == null) {
            throw new ToolbarInteractionException("Toolbar is a required field, must set before calling build");
        }
        if (toolbarBackground == null) {
            toolbarBackground = new ColorDrawable(context.getResources().getColor(Color.TRANSPARENT));
        }
        return new ToolbarOptions(toolbar, title, toolbarBackground, backDrawableID, attachToNavDrawer, navDrawerBackDrawableID, menuID, drawerNavState);
    }

    public ToolbarOptionsBuilder setAttachToNavDrawer(boolean flag) {
        attachToNavDrawer = flag;
        return this;
    }

    public ToolbarOptionsBuilder setBackDrawableID(int i) {
        backDrawableID = i;
        return this;
    }

    public ToolbarOptionsBuilder setDrawerNavState(CoreNavigationActivity.DrawerNavState drawernavstate) {
        drawerNavState = drawernavstate;
        return this;
    }

    public ToolbarOptionsBuilder setMenuID(int i) {
        menuID = i;
        return this;
    }

    public ToolbarOptionsBuilder setNavDrawerBackDrawableID(int i) {
        navDrawerBackDrawableID = i;
        return this;
    }

    public ToolbarOptionsBuilder setTitle(String s) {
        title = s;
        return this;
    }

    public ToolbarOptionsBuilder setToolbar(Toolbar toolbar1) {
        toolbar = toolbar1;
        return this;
    }

    public ToolbarOptionsBuilder setToolbarBackground(Drawable drawable) {
        toolbarBackground = drawable;
        return this;
    }
}

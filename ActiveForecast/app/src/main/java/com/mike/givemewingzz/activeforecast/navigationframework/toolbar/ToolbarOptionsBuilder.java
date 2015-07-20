package com.mike.givemewingzz.activeforecast.navigationframework.toolbar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;

import com.mike.givemewingzz.activeforecast.R;
import com.mike.givemewingzz.activeforecast.navigationframework.navigation.CoreNavigationActivity;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbarframework.exceptions.ToolbarInteractionException;

public class ToolbarOptionsBuilder {
    private Toolbar toolbar;
    private String title = "";
    private Drawable toolbarBackground;
    private int backDrawableID = -1;
    private int menuID = -1;
    private boolean attachToNavDrawer = false;
    private int navDrawerBackDrawableID = R.drawable.appbar_back_themed;
    private CoreNavigationActivity.DrawerNavState drawerNavState = CoreNavigationActivity.DrawerNavState.NONE;

    public ToolbarOptionsBuilder setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        return this;
    }

    public ToolbarOptionsBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ToolbarOptionsBuilder setToolbarBackground(Drawable toolbarBackground) {
        this.toolbarBackground = toolbarBackground;
        return this;
    }

    public ToolbarOptionsBuilder setBackDrawableID(int backDrawableID) {
        this.backDrawableID = backDrawableID;
        return this;
    }

    public ToolbarOptionsBuilder setAttachToNavDrawer(boolean attachToNavDrawer) {
        this.attachToNavDrawer = attachToNavDrawer;
        return this;
    }

    public ToolbarOptionsBuilder setNavDrawerBackDrawableID(int navDrawerBackDrawableID) {
        this.navDrawerBackDrawableID = navDrawerBackDrawableID;
        return this;
    }

    public ToolbarOptionsBuilder setMenuID(int menuID) {
        this.menuID = menuID;
        return this;
    }

    public ToolbarOptionsBuilder setDrawerNavState(CoreNavigationActivity.DrawerNavState drawerNavState) {
        this.drawerNavState = drawerNavState;
        return this;
    }

    public ToolbarOptions build(Context context) throws ToolbarInteractionException {
        if (toolbar == null) {
            throw new ToolbarInteractionException("Toolbar is a required field, must set before calling build");
        }

        if (toolbarBackground == null) {
            toolbarBackground = new ColorDrawable(context.getResources().getColor(R.color.action_bar_gray));
        }

        return new ToolbarOptions(toolbar, title, toolbarBackground, backDrawableID, attachToNavDrawer, navDrawerBackDrawableID, menuID, drawerNavState);
    }
}
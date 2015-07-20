package com.mike.givemewingzz.activeforecast.navigationframework.toolbar;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;

import com.mike.givemewingzz.activeforecast.navigationframework.navigation.CoreNavigationActivity;

public class ToolbarOptions {

	private Toolbar toolbar;
	private String title;
	private Drawable toolbarBackground;
	private int navDrawableID;
	private boolean attachToNavDrawer;
	private CoreNavigationActivity.DrawerNavState drawerNavState;
	private int navDrawerBackDrawableID;
	private int menuID;

	public ToolbarOptions(Toolbar toolbar, String title,
			Drawable toolbarBackground, int navDrawableID,
			boolean attachToNavDrawer, int navDrawerBackDrawableID, int menuID,
			CoreNavigationActivity.DrawerNavState drawerNavState) {
		this.toolbar = toolbar;
		this.title = title;
		this.toolbarBackground = toolbarBackground;
		this.navDrawableID = navDrawableID;
		this.attachToNavDrawer = attachToNavDrawer;
		this.navDrawerBackDrawableID = navDrawerBackDrawableID;
		this.menuID = menuID;
		this.drawerNavState = drawerNavState;
	}

	public Toolbar getToolbar() {
		return toolbar;
	}

	public String getTitle() {
		return title;
	}

	public Drawable getToolbarBackground() {
		return toolbarBackground;
	}

	public int getNavDrawableID() {
		return navDrawableID;
	}

	public boolean attachToNavDrawer() {
		return attachToNavDrawer;
	}

	public int getNavDrawerBackDrawableID() {
		return navDrawerBackDrawableID;
	}

	public int getMenuID() {
		return menuID;
	}

	public CoreNavigationActivity.DrawerNavState getDrawerNavState() {
		return drawerNavState;
	}
}

package com.mike.givemewingzz.activeforecast.navigationdrawer;

import android.graphics.drawable.Drawable;

public class NavigationModel {

    private String navLabel;
    private Drawable navLabelSelected;
    private Drawable navLabelUnSelected;

    public NavigationModel(String s, Drawable drawable, Drawable drawable1) {
        navLabel = s;
        navLabelSelected = drawable;
        navLabelUnSelected = drawable1;
    }

    public Drawable getNavLabelSelected() {
        return navLabelSelected;
    }

    public Drawable getNavLabelUnSelected() {
        return navLabelUnSelected;
    }

    public String getlabel() {
        return navLabel;
    }
}

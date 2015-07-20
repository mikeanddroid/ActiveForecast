package com.mike.givemewingzz.activeforecast.navigationframework.navigation;

import android.graphics.drawable.Drawable;

public class NavigationModel {

    private String navLabel;
    private Drawable navLabelSelected;
    private Drawable navLabelUnSelected;

    public NavigationModel(String navLabel, Drawable navLabelSelected, Drawable navLabelUnSelected) {

        this.navLabel = navLabel;
        this.navLabelSelected = navLabelSelected;
        this.navLabelUnSelected = navLabelUnSelected;

    }

    public String getlabel() {
        return navLabel;
    }

    public Drawable getNavLabelSelected() {
        return navLabelSelected;
    }

    public Drawable getNavLabelUnSelected() {
        return navLabelUnSelected;
    }

}

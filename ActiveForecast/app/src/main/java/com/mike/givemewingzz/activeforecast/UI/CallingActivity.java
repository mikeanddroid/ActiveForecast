package com.mike.givemewingzz.activeforecast.UI;

import android.os.Bundle;

import com.mike.givemewingzz.activeforecast.navigationframework.navigation.CoreNavigationActivity;

/**
 * Created by GiveMeWingzz on 7/23/2015.
 */
public class CallingActivity extends CoreNavigationActivity {

    public static final String TAG = CallingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment(CallingFragment.getInstance(),true, false);
    }

}

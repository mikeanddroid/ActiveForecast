package com.mike.givemewingzz.activeforecast.UI;

import android.os.Bundle;

import com.mike.givemewingzz.activeforecast.navigationframework.navigation.CoreNavigationActivity;

public class TestClassThreeActivity extends CoreNavigationActivity
{

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        loadFragment(TestClassThree.getInstance(), true, false);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setSelectedNavigationIndex(2);
    }
}

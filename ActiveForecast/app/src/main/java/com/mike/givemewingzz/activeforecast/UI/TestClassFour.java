// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.mike.givemewingzz.activeforecast.UI;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.givemewingz.activeforecast.mike.activeweather.CoreFragment;
import com.example.givemewingz.activeforecast.mike.applicationhandlers.FragmentTransactionHandler;
import com.example.givemewingz.activeforecast.mike.exceptions.ToolbarInteractionException;
import com.example.givemewingz.activeforecast.mike.toolbar.utils.ToolbarOptionsBuilder;

public class TestClassFour extends CoreFragment
{

    private static final String TAG = com/example/givemewingz/activeforecast/mike/core/TestClassFour.getSimpleName();
    private FragmentTransactionHandler fragmentTransactionHandler;

    public TestClassFour()
    {
    }

    public static TestClassFour getInstance()
    {
        return new TestClassFour();
    }

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (activity instanceof FragmentTransactionHandler)
        {
            fragmentTransactionHandler = (FragmentTransactionHandler)activity;
        }
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        layoutinflater = layoutinflater.inflate(0x7f04002c, viewgroup, false);
        try
        {
            viewgroup = (Toolbar)layoutinflater.findViewById(0x7f0c0079);
            setToolbar((new ToolbarOptionsBuilder()).setToolbar(viewgroup).setToolbarBackground(new ColorDrawable(getResources().getColor(0x7f0b004d))).setAttachToNavDrawer(true).setDrawerNavState(com.example.givemewingz.activeforecast.mike.navigation.CoreNavigationActivity.DrawerNavState.TOGGLE).build(getActivity()));
        }
        // Misplaced declaration of an exception variable
        catch (ViewGroup viewgroup)
        {
            Log.e(TAG, "Something went wrong", viewgroup);
            return layoutinflater;
        }
        return layoutinflater;
    }

}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.mike.givemewingzz.activeforecast.UI;

import android.os.Bundle;
import com.example.givemewingz.activeforecast.mike.navigation.CoreNavigationActivity;

// Referenced classes of package com.example.givemewingz.activeforecast.mike.core:
//            TestClassThree

public class TestClassThreeActivity extends CoreNavigationActivity
{

    public TestClassThreeActivity()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        loadFragment(TestClassThree.getInstance(), true, false);
    }

    protected void onResume()
    {
        super.onResume();
        setSelectedNavigationIndex(2);
    }
}

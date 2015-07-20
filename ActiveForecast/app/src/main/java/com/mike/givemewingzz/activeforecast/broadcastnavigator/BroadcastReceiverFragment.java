// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.mike.givemewingzz.activeforecast.broadcastnavigator;

import android.content.Intent;
import java.util.Set;

public interface BroadcastReceiverFragment
{

    public abstract String getFragmentID();

    public abstract Set getIntentFilters();

    public abstract void receiveBroadcast(Intent intent);

    public abstract void receiveFailedBroadcast(Intent intent);

    public abstract void routeBroadcast(Intent intent);
}

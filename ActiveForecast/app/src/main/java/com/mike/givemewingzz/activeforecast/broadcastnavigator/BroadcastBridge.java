// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.mike.givemewingzz.activeforecast.broadcastnavigator;


// Referenced classes of package com.example.givemewingz.activeforecast.mike.services.broadcasthandler:
//            BroadcastReceiverFragment

public interface BroadcastBridge
{

    public abstract void registerForBroadcast(BroadcastReceiverFragment broadcastreceiverfragment, String s);

    public abstract void registerForBroadcasts(BroadcastReceiverFragment broadcastreceiverfragment);

    public abstract void unRegisterForBroadcast(String s, String s1);

    public abstract void unRegisterForBroadcasts(String s);
}

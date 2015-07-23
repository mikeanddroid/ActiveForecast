// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.os.Handler;
import android.util.Log;

// Referenced classes of package com.example.givemewingz.activeforecast.mike.services:
//            NetworkService, ResponseObject

public class ReceiverThread extends Thread
{

    public static final String TAG = "ReceiverThread";
    private Handler mHandler;
    private final NetworkService networkService;
    final ResponseObject responseObj;

    public ReceiverThread(NetworkService networkservice, ResponseObject responseobject)
    {
        networkService = networkservice;
        responseObj = responseobject;
    }

    public void run()
    {
        long l;
        l = System.currentTimeMillis();
        Log.i(NetworkService.TAG, (new StringBuilder()).append("ReceiverThread startTime: ").append(l).toString());
        try
        {
            networkService.processResponse(responseObj);
        }
        catch (Throwable throwable)
        {
            try
            {
                Log.e("ReceiverThread", (new StringBuilder()).append(throwable).append("").toString());
            }
            catch (Exception exception)
            {
                Log.e("ReceiverThread", "Error processing Response Object ", exception);
                return;
            }
        }
        Log.i(NetworkService.TAG, (new StringBuilder()).append("ReceiverThread endTime: ").append(System.currentTimeMillis() - l).toString());
        return;
    }
}

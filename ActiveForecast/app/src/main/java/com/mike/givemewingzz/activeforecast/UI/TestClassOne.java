// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.mike.givemewingzz.activeforecast.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.givemewingz.activeforecast.mike.activeweather.CoreFragment;
import com.example.givemewingz.activeforecast.mike.application.servermapping.data.objects.currentweather.ActualData;
import com.example.givemewingz.activeforecast.mike.application.servermapping.data.objects.currentweather.CurrentWeather;
import com.example.givemewingz.activeforecast.mike.application.servermapping.data.objects.currentweather.OtherData;
import com.example.givemewingz.activeforecast.mike.application.servermapping.data.objects.currentweather.Weather;
import com.example.givemewingz.activeforecast.mike.application.servermapping.data.objects.currentweather.WindData;
import com.example.givemewingz.activeforecast.mike.applicationhandlers.FragmentTransactionHandler;
import com.example.givemewingz.activeforecast.mike.exceptions.ToolbarInteractionException;
import com.example.givemewingz.activeforecast.mike.services.DatabaseManager;
import com.example.givemewingz.activeforecast.mike.services.broadcasthandler.BroadcastReceiverFragment;
import com.example.givemewingz.activeforecast.mike.services.process.dispatch.RequestDispatch;
import com.example.givemewingz.activeforecast.mike.toolbar.utils.ToolbarOptionsBuilder;
import com.example.givemewingz.activeforecast.mike.views.SwipeLayoutLinearChild;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Referenced classes of package com.example.givemewingz.activeforecast.mike.core:
//            CoreApplication

public class TestClassOne extends CoreFragment
    implements BroadcastReceiverFragment, com.example.givemewingz.activeforecast.mike.services.DatabaseManager.AsyncResponseListener, SwipeRefreshLayout.OnRefreshListener
{

    private static final String TAG = com/example/givemewingz/activeforecast/mike/core/TestClassOne.getSimpleName();
    List actualDataList;
    List currentDataList;
    private FragmentTransactionHandler fragmentTransactionHandler;
    private SwipeRefreshLayout swipeLayout;
    private SwipeLayoutLinearChild swipeLayoutLinearChild;
    List sysDataList;
    List weatherDataList;
    List windDataList;

    public TestClassOne()
    {
        actualDataList = new ArrayList();
        weatherDataList = new ArrayList();
        sysDataList = new ArrayList();
        windDataList = new ArrayList();
        currentDataList = new ArrayList();
    }

    private void fetchNewhData(String s)
    {
        swipeLayout.setVisibility(0);
        swipeLayout.setRefreshing(true);
        CoreApplication.getInstance().getRequestDispatch().requestCurrentWeatherData(false, s);
    }

    public static TestClassOne getInstance()
    {
        return new TestClassOne();
    }

    private void setupSwipeToRefresh(View view)
    {
        swipeLayout = (SwipeRefreshLayout)view.findViewById(0x7f0c007a);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(new int[] {
            CoreApplication.context.getResources().getColor(0x7f0b004e)
        });
    }

    private void setupToolbar(View view)
    {
        try
        {
            view = (Toolbar)view.findViewById(0x7f0c0079);
            setToolbar((new ToolbarOptionsBuilder()).setToolbar(view).setToolbarBackground(new ColorDrawable(getResources().getColor(0x7f0b0021))).setAttachToNavDrawer(true).setDrawerNavState(com.example.givemewingz.activeforecast.mike.navigation.CoreNavigationActivity.DrawerNavState.TOGGLE).build(getActivity()));
            return;
        }
        // Misplaced declaration of an exception variable
        catch (View view)
        {
            Log.e(TAG, "Something went wrong", view);
        }
    }

    public Set getIntentFilters()
    {
        return new HashSet(Arrays.asList(new String[] {
            "CURRENT_WEATHER_DATA"
        }));
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

    public View onCreateView(final LayoutInflater rootView, ViewGroup viewgroup, Bundle bundle)
    {
        rootView = rootView.inflate(0x7f04002b, viewgroup, false);
        setupToolbar(rootView);
        viewgroup = (RelativeLayout)rootView.findViewById(0x7f0c007c);
        (new Handler()).postDelayed(new Runnable() {

            final TestClassOne this$0;
            final View val$rootView;

            public void run()
            {
                setupSwipeToRefresh(rootView);
                fetchNewhData("20770");
            }

            
            {
                this$0 = TestClassOne.this;
                rootView = view;
                super();
            }
        }, 20L);
        swipeLayoutLinearChild = (SwipeLayoutLinearChild)rootView.findViewById(0x7f0c007b);
        swipeLayoutLinearChild.setScrollableChild(viewgroup);
        return rootView;
    }

    public void onRefresh()
    {
        Log.d(TAG, "Swipe to refresh called, making new request for data from server.");
        CoreApplication.getInstance().getRequestDispatch().requestCurrentWeatherData(false, "20191");
    }

    public void onRequestComplete(com.example.givemewingz.activeforecast.mike.services.DatabaseManager.AsyncResponse asyncresponse)
    {
        Log.d(TAG, (new StringBuilder()).append("Inside onRequestComplete. Response Size : Actual Data Size : ").append(asyncresponse.actualDataList.size()).toString());
        for (int i = 0; i < asyncresponse.responseData.size(); i++)
        {
            Log.d(TAG, (new StringBuilder()).append("Response Data List : ").append(asyncresponse.responseData.get(i)).toString());
        }

        if (actualDataList != null)
        {
            actualDataList.clear();
            actualDataList.addAll(asyncresponse.actualDataList);
            for (int j = 0; j < asyncresponse.actualDataList.size(); j++)
            {
                Log.d(TAG, (new StringBuilder()).append("Actual Data List Current Temp : ").append(((ActualData)actualDataList.get(j)).currentTempInCelsius).toString());
                Log.d(TAG, (new StringBuilder()).append("Actual Data List Current Temp in Farenheit: ").append(((ActualData)actualDataList.get(j)).currentTempInFarenheit).toString());
            }

        }
        if (currentDataList != null)
        {
            currentDataList.clear();
            currentDataList.addAll(asyncresponse.currentDataList);
            for (int k = 0; k < asyncresponse.currentDataList.size(); k++)
            {
                Log.d(TAG, (new StringBuilder()).append("Current Data List City : ").append(((CurrentWeather)currentDataList.get(k)).cityName).toString());
            }

        }
        if (weatherDataList != null)
        {
            weatherDataList.clear();
            weatherDataList.addAll(asyncresponse.weatherDataList);
            for (int l = 0; l < asyncresponse.weatherDataList.size(); l++)
            {
                Log.d(TAG, (new StringBuilder()).append("Weather Data List Weather Desc : ").append(((Weather)weatherDataList.get(l)).weatherDescription).toString());
            }

        }
        if (windDataList != null)
        {
            windDataList.clear();
            windDataList.addAll(asyncresponse.windDataList);
            for (int i1 = 0; i1 < asyncresponse.windDataList.size(); i1++)
            {
                Log.d(TAG, (new StringBuilder()).append("Wind Data List WindSpeed : ").append(((WindData)windDataList.get(i1)).windSpeed).toString());
            }

        }
        if (sysDataList != null)
        {
            sysDataList.clear();
            sysDataList.addAll(asyncresponse.sysDataList);
            DateFormat dateformat = DateFormat.getDateTimeInstance();
            for (int j1 = 0; j1 < asyncresponse.sysDataList.size(); j1++)
            {
                String s = dateformat.format(new Date(((OtherData)sysDataList.get(j1)).sunrise));
                Log.d(TAG, (new StringBuilder()).append("Other Data List Sunrise : ").append(s).toString());
            }

        }
    }

    public void receiveBroadcast(Intent intent)
    {
        byte byte0;
        intent = intent.getAction();
        byte0 = -1;
        intent.hashCode();
        JVM INSTR tableswitch -110495141 -110495141: default 28
    //                   -110495141 49;
           goto _L1 _L2
_L2:
        if (intent.equals("CURRENT_WEATHER_DATA"))
        {
            byte0 = 0;
        }
_L1:
        switch (byte0)
        {
        default:
            return;

        case 0: // '\0'
            Log.d(TAG, "Test Class Received Action SUCCESS");
            break;
        }
        CoreApplication.getInstance().getDBCursorManager().fetchCurrentWeather(this);
        Cursor cursor = CoreApplication.getInstance().getDBCursorManager().fetchActualDataCursor();
        swipeLayout.setRefreshing(false);
        intent = null;
        if (cursor.moveToNext())
        {
            intent = cursor.getString(cursor.getColumnIndexOrThrow("MaxTemperature"));
        }
        Log.d(TAG, (new StringBuilder()).append("Max Temp : ").append(intent).toString());
        return;
    }

    public void receiveFailedBroadcast(Intent intent)
    {
        byte byte0;
        intent = intent.getAction();
        byte0 = -1;
        intent.hashCode();
        JVM INSTR tableswitch -110495141 -110495141: default 28
    //                   -110495141 49;
           goto _L1 _L2
_L2:
        if (intent.equals("CURRENT_WEATHER_DATA"))
        {
            byte0 = 0;
        }
_L1:
        switch (byte0)
        {
        default:
            return;

        case 0: // '\0'
            Log.d(TAG, "Test Class Received Action FAILURE");
            break;
        }
        swipeLayout.setRefreshing(false);
        swipeLayout.setVisibility(8);
        return;
    }



}

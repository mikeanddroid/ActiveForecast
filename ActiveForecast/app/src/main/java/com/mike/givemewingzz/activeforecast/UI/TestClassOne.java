package com.mike.givemewingzz.activeforecast.UI;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mike.givemewingzz.activeforecast.R;
import com.mike.givemewingzz.activeforecast.applicationhandlers.FragmentTransactionHandler;
import com.mike.givemewingzz.activeforecast.baseclasses.CoreFragment;
import com.mike.givemewingzz.activeforecast.broadcastframework.broadcastnavigator.BroadcastReceiverFragment;
import com.mike.givemewingzz.activeforecast.navigationframework.navigation.CoreNavigationActivity;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbar.ToolbarOptions;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbar.ToolbarOptionsBuilder;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbarframework.exceptions.ToolbarInteractionException;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.DatabaseFramework.DatabaseManager;
import com.mike.givemewingzz.activeforecast.servermapping.ActualData;
import com.mike.givemewingzz.activeforecast.servermapping.CurrentWeather;
import com.mike.givemewingzz.activeforecast.servermapping.OtherData;
import com.mike.givemewingzz.activeforecast.servermapping.Weather;
import com.mike.givemewingzz.activeforecast.servermapping.WindData;
import com.mike.givemewingzz.activeforecast.swipetorefresh.SwipeLayoutLinearChild;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestClassOne extends CoreFragment implements BroadcastReceiverFragment, DatabaseManager.AsyncResponseListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = TestClassOne.class.getSimpleName();

    List<ActualData> actualDataList;
    List<CurrentWeather> currentDataList;
    List<OtherData> sysDataList;
    List<Weather> weatherDataList;
    List<WindData> windDataList;

    private FragmentTransactionHandler fragmentTransactionHandler;

    // START Views //
    private View rootView;

    private SwipeRefreshLayout swipeLayout;
    private SwipeLayoutLinearChild swipeLayoutLinearChild;


    public static TestClassOne getInstance() {
        return new TestClassOne();
    }

    public TestClassOne() {
        actualDataList = new ArrayList();
        weatherDataList = new ArrayList();
        sysDataList = new ArrayList();
        windDataList = new ArrayList();
        currentDataList = new ArrayList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentTransactionHandler) {
            fragmentTransactionHandler = (FragmentTransactionHandler) activity;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater infalter, ViewGroup viewgroup, Bundle bundle) {

        rootView = infalter.inflate(R.layout.testclasslayout, viewgroup, false);

        setupToolbar(rootView);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                setupSwipeToRefresh(rootView);
                fetchNewhData("20770");
            }

        }, 10L);

        swipeLayoutLinearChild = (SwipeLayoutLinearChild) rootView.findViewById(R.id.swipe_linear_child);

        swipeLayoutLinearChild.setScrollableChild(viewgroup);
        return rootView;
    }

    private void fetchNewhData(String zipCode) {
        swipeLayout.setVisibility(View.VISIBLE);
        swipeLayout.setRefreshing(true);
        CoreApplication.getInstance().getRequestDispatch().requestCurrentWeatherData(false, zipCode);
    }



    private void setupSwipeToRefresh(View view) {
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(new int[]{
                CoreApplication.context.getResources().getColor(R.color.ttdPrimaryColor)
        });
    }

    public Set getIntentFilters() {
        return new HashSet(Arrays.asList(ApplicationUtils.Receivers.CURRENT_WEATHER_DATA));
    }

    private void setupToolbar(View view) {
        try {
            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_actionbar);
            ToolbarOptions options = new ToolbarOptionsBuilder()
                    .setToolbar(toolbar)
                    .setTitle("Example Title")
                    .setMenuID(R.menu.menu_main)
                    .setAttachToNavDrawer(true)
                    .setDrawerNavState(CoreNavigationActivity.DrawerNavState.TOGGLE).build(getActivity());

            setToolbar(options);
        } catch (ToolbarInteractionException e) {
            Log.e(TAG, ApplicationUtils.ApplicationConstants.LOG_TAG_SOMETHING_WENT_WRONG, e);
        }
    }

    /**
     * Called when the user pulls down on the event list.
     */
    @Override
    public void onRefresh() {
        Log.d(TAG, "Swipe to refresh called, making new request for data from server.");
        CoreApplication.getInstance().getRequestDispatch().requestCurrentWeatherData(false, "20191");
    }

    @Override
    public void onRequestComplete(DatabaseManager.AsyncResponse asyncresponse) {
        Log.d(TAG, (new StringBuilder()).append("Inside onRequestComplete. Response Size : Actual Data Size : ").append(asyncresponse.actualDataList.size()).toString());
        for (int i = 0; i < asyncresponse.completeList.size(); i++) {
            Log.d(TAG, (new StringBuilder()).append("Response Data List : ").append(asyncresponse.completeList.get(i)).toString());
        }

        if (actualDataList != null) {
            actualDataList.clear();
            actualDataList.addAll(asyncresponse.actualDataList);
            for (int j = 0; j < asyncresponse.actualDataList.size(); j++) {
                Log.d(TAG, (new StringBuilder()).append("Actual Data List Current Temp : ").append(((ActualData) actualDataList.get(j)).currentTempInCelsius).toString());
                Log.d(TAG, (new StringBuilder()).append("Actual Data List Current Temp in Farenheit: ").append(((ActualData) actualDataList.get(j)).currentTempInFarenheit).toString());
            }

        }
        if (currentDataList != null) {
            currentDataList.clear();
            currentDataList.addAll(asyncresponse.currentDataList);
            for (int k = 0; k < asyncresponse.currentDataList.size(); k++) {
                Log.d(TAG, (new StringBuilder()).append("Current Data List City : ").append(((CurrentWeather) currentDataList.get(k)).cityName).toString());
            }

        }
        if (weatherDataList != null) {
            weatherDataList.clear();
            weatherDataList.addAll(asyncresponse.weatherDataList);
            for (int l = 0; l < asyncresponse.weatherDataList.size(); l++) {
                Log.d(TAG, (new StringBuilder()).append("Weather Data List Weather Desc : ").append(((Weather) weatherDataList.get(l)).weatherDescription).toString());
            }

        }
        if (windDataList != null) {
            windDataList.clear();
            windDataList.addAll(asyncresponse.windDataList);
            for (int i1 = 0; i1 < asyncresponse.windDataList.size(); i1++) {
                Log.d(TAG, (new StringBuilder()).append("Wind Data List WindSpeed : ").append(((WindData) windDataList.get(i1)).windSpeed).toString());
            }

        }
        if (sysDataList != null) {
            sysDataList.clear();
            sysDataList.addAll(asyncresponse.sysDataList);
            DateFormat dateformat = DateFormat.getDateTimeInstance();
            for (int j1 = 0; j1 < asyncresponse.sysDataList.size(); j1++) {
                String s = dateformat.format(new Date(((OtherData) sysDataList.get(j1)).sunrise));
                Log.d(TAG, (new StringBuilder()).append("Other Data List Sunrise : ").append(s).toString());
            }

        }
    }

    @Override
    public void receiveBroadcast(Intent intent) {

        switch (intent.getAction()){

            case ApplicationUtils.Receivers.CURRENT_WEATHER_DATA:

                CoreApplication.getInstance().getDBCursorManager().fetchCurrentWeather(this);
                Cursor cursor = CoreApplication.getInstance().getDBCursorManager().fetchActualDataCursor();
                swipeLayout.setRefreshing(false);
                String maxTemp = null;
                if (cursor.moveToNext()) {
                    maxTemp = cursor.getString(cursor.getColumnIndexOrThrow("MaxTemperature"));
                }
                Log.d(TAG, (new StringBuilder()).append("Max Temp : ").append(maxTemp).toString());

                break;


        }

    }

    @Override
    public void receiveFailedBroadcast(Intent intent) {
        super.receiveFailedBroadcast(intent);
        switch (intent.getAction()) {

            case ApplicationUtils.Receivers.CURRENT_WEATHER_DATA:
                Log.d(TAG, "Test Class Received Action FAILURE");
                break;

        }
        swipeLayout.setRefreshing(false);
        swipeLayout.setVisibility(View.GONE);
        return;
    }


}

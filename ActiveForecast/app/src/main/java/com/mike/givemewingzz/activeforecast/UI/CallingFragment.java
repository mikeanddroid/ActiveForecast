package com.mike.givemewingzz.activeforecast.UI;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mike.givemewingzz.activeforecast.R;
import com.mike.givemewingzz.activeforecast.applicationhandlers.FragmentTransactionHandler;
import com.mike.givemewingzz.activeforecast.baseclasses.CoreFragment;
import com.mike.givemewingzz.activeforecast.broadcastframework.broadcastnavigator.BroadcastReceiverFragment;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbar.ToolbarOptions;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbar.ToolbarOptionsBuilder;
import com.mike.givemewingzz.activeforecast.navigationframework.toolbarframework.exceptions.ToolbarInteractionException;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.DatabaseFramework.DatabaseManager;
import com.mike.givemewingzz.activeforecast.servermapping.ActualData;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by GiveMeWingzz on 7/23/2015.
 */
public class CallingFragment extends CoreFragment implements BroadcastReceiverFragment, DatabaseManager.AsyncResponseListener, View.OnClickListener {

    public static final String TAG = CallingFragment.class.getSimpleName();

    private FragmentTransactionHandler fragmentTransactionHandler;

    private List<ActualData> actualDataList;

    public CallingFragment() {

        actualDataList = new ArrayList<>();

    }

    public static CallingFragment getInstance() {

        return new CallingFragment();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup viewgroup, Bundle bundle) {

        View rootView = inflater.inflate(R.layout.xyz, viewgroup, false);

        setupToolbar(rootView);

        Button requestButton = (Button) rootView.findViewById(R.id.calldispatch);

        requestButton.setOnClickListener(this);

        return rootView;
    }

    private void setupToolbar(View rootView) {
        try {
            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar_actionbar);
            ToolbarOptions options = new ToolbarOptionsBuilder()
                    .setToolbar(toolbar)
                    .setTitle("Example Title")
                    .setMenuID(R.menu.menu_main)
                    .setAttachToNavDrawer(false)
                    .build(getActivity());

            setToolbar(options);
        } catch (ToolbarInteractionException e) {
            Log.e(TAG, ApplicationUtils.ApplicationConstants.LOG_TAG_SOMETHING_WENT_WRONG, e);
        }
    }

    @Override
    public Set<String> getIntentFilters() {
        return new HashSet(Arrays.asList(
                ApplicationUtils.Receivers.CURRENT_WEATHER_DATA,
                ApplicationUtils.Receivers.HOURLY_WEATHER_DATA,
                ApplicationUtils.Receivers.FORECAST_WEATHER_DATA,
                ApplicationUtils.Receivers.HISTORIC_WEATHER_DATA));
    }

    @Override
    public void receiveFailedBroadcast(Intent intent) {

        switch (intent.getAction()) {

            case ApplicationUtils.Receivers.CURRENT_WEATHER_DATA:

                Log.d(TAG, "CURRENT_WEATHER_DATA Broadcast Failed!!");

                break;

            case ApplicationUtils.Receivers.HOURLY_WEATHER_DATA:

                Log.d(TAG, "HOURLY_WEATHER_DATA Broadcast Failed!!");

                break;
            case ApplicationUtils.Receivers.FORECAST_WEATHER_DATA:

                Log.d(TAG, "FORECAST_WEATHER_DATA Broadcast Failed!!");

                break;
            case ApplicationUtils.Receivers.HISTORIC_WEATHER_DATA:

                Log.d(TAG, "HISTORIC_WEATHER_DATA Broadcast Failed!!");

                break;

        }

    }


    @Override
    public void receiveBroadcast(Intent intent) {

        switch (intent.getAction()) {

            case ApplicationUtils.Receivers.CURRENT_WEATHER_DATA:

                CoreApplication.getInstance().getDBCursorManager().fetchCurrentWeather(this);
                Cursor cursor = CoreApplication.getInstance().getDBCursorManager().fetchActualDataCursor();

                String maxTemp = null;

                if (cursor.moveToNext()) {
                    maxTemp = cursor.getString(cursor.getColumnIndexOrThrow("MaxTemperature"));
                }

                Log.d(TAG, (new StringBuilder()).append("Max Temp : ").append(maxTemp).toString());

                break;


            case ApplicationUtils.Receivers.HOURLY_WEATHER_DATA:

                Log.d(TAG, "HOURLY_WEATHER_DATA Broadcast!!");

                break;
            case ApplicationUtils.Receivers.FORECAST_WEATHER_DATA:

                Log.d(TAG, "FORECAST_WEATHER_DATA Broadcast!!");

                break;
            case ApplicationUtils.Receivers.HISTORIC_WEATHER_DATA:

                Log.d(TAG, "HISTORIC_WEATHER_DATA Broadcast!!");

                break;

        }

    }


    @Override
    public void onRequestComplete(DatabaseManager.AsyncResponse asyncresponse) {

        Log.d(TAG, (new StringBuilder())
                .append("Inside onRequestComplete. Response Size : Actual Data Size : ")
                .append(asyncresponse.actualDataList.size()).toString());

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

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.calldispatch:
                CoreApplication.getInstance().getRequestDispatch().requestCurrentWeatherData(false, "20770");

                Log.d(TAG,"Whatever");

                break;

        }

    }
}

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
import com.mike.givemewingzz.activeforecast.servermapping.CurrentWeather;
import com.mike.givemewingzz.activeforecast.servermapping.ItunesModel;
import com.mike.givemewingzz.activeforecast.servermapping.OtherData;
import com.mike.givemewingzz.activeforecast.servermapping.Weather;
import com.mike.givemewingzz.activeforecast.servermapping.WindData;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by GiveMeWingzz on 7/23/2015.
 */
public class CallingFragment extends CoreFragment implements BroadcastReceiverFragment, DatabaseManager.AsyncResponseListener, View.OnClickListener, DatabaseManager.ItunesResponseListener {

    public static final String TAG = CallingFragment.class.getSimpleName();

    private FragmentTransactionHandler fragmentTransactionHandler;

    private List<ActualData> actualDataList;
    List<CurrentWeather> currentDataList;
    List<OtherData> sysDataList;
    List<Weather> weatherDataList;
    List<WindData> windDataList;

    List<ItunesModel> itunesModelList;

    private Button requestButton;
    private Button itunesRequestButton;

    /**/

    public CallingFragment() {

        actualDataList = new ArrayList<>();
        weatherDataList = new ArrayList();
        sysDataList = new ArrayList();
        windDataList = new ArrayList();
        currentDataList = new ArrayList();
        itunesModelList = new ArrayList<>();

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

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView){

        requestButton = (Button) rootView.findViewById(R.id.calldispatch);
        itunesRequestButton = (Button) rootView.findViewById(R.id.itunesdispatch);

        requestButton.setOnClickListener(this);
        itunesRequestButton.setOnClickListener(this);

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
                ApplicationUtils.Receivers.HISTORIC_WEATHER_DATA,
                ApplicationUtils.Receivers.ITUNES_DATA_FILTER));
    }

    @Override
    public void receiveFailedBroadcast(Intent intent) {

        switch (intent.getAction()) {

            case ApplicationUtils.Receivers.CURRENT_WEATHER_DATA:

                Log.d(TAG, "CURRENT_WEATHER_DATA Broadcast Failed!!");
                break;

            case ApplicationUtils.Receivers.ITUNES_DATA_FILTER:

                Log.d(TAG, "ITUNES_DATA_FILTER Broadcast Failed!!");

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

                break;

            case ApplicationUtils.Receivers.ITUNES_DATA_FILTER:

                Log.d(TAG,"Stop of the Request in ITUNES_DATA_FILTER");

                CoreApplication.getInstance().getDBCursorManager().fetchItunesData(this);
                Cursor itunesCursor = CoreApplication.getInstance().getDBCursorManager().fetchItunesCursor();

                if (itunesCursor.moveToNext()) {
                    Log.d(TAG, "ITUNES DATA SIZE : " + itunesCursor.getCount());
                }

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
    public void onItunesRequestComplete(DatabaseManager.ItunesResponse itunesResponse) {

        Log.d(TAG,"Stop of the Request in onItunesRequestComplete");

        Log.d(TAG, "Inside onItunesRequestComplete. Response Size : Itunes Data Size : " +
                itunesResponse.mainList.size());

        if (itunesModelList != null) {
            itunesModelList.clear();
            itunesModelList.addAll(itunesResponse.itunesModel);
            for (int j = 0; j < itunesResponse.itunesModel.size(); j++) {

                Log.d(TAG, "Itunes Data List ArtistId : " + itunesModelList.get(j).artistId);
                Log.d(TAG, "Itunes  Data List Artist Name : " + itunesModelList.get(j).artistName);
                Log.d(TAG,"Itunes  Data List ArtistViewUrl : "+ itunesModelList.get(j).artistViewUrl);
                Log.d(TAG,"Itunes  Data List Art100 : "+ itunesModelList.get(j).artworkUrl100);
                Log.d(TAG,"Itunes  Data List Art 60 : "+ itunesModelList.get(j).artworkUrl60);
                Log.d(TAG,"Itunes  Data List Collection Sensor : "+ itunesModelList.get(j).collectionCensoredName);
                Log.d(TAG,"Itunes  Data List Collection Explicitness : "+ itunesModelList.get(j).collectionExplicitness);
                Log.d(TAG,"Itunes  Data List Collection Name : "+ itunesModelList.get(j).collectionName);
                Log.d(TAG,"Itunes  Data List Country : "+ itunesModelList.get(j).country);
                Log.d(TAG,"Itunes  Data List Currency : "+ itunesModelList.get(j).currency);
                Log.d(TAG,"Itunes  Data List Kind : "+ itunesModelList.get(j).kind);

            }
        }

    }

    @Override
    public void onRequestComplete(DatabaseManager.AsyncResponse asyncresponse) {

        Log.d(TAG, (new StringBuilder())
                .append("Inside onRequestComplete. Response Size : Actual Data Size : ")
                .append(asyncresponse.actualDataList.size()).toString());

        if (actualDataList != null) {
            actualDataList.clear();
            actualDataList.addAll(asyncresponse.actualDataList);
            for (int j = 0; j < asyncresponse.actualDataList.size(); j++) {

                Log.d(TAG, "Actual Data List Current Temp : " + actualDataList.get(j).currentTempInCelsius);
                Log.d(TAG, "Actual Data List Current Temp in Farenheit : " + actualDataList.get(j).currentTempInFarenheit);
                Log.d(TAG,"Actual Data List Humidity : "+ actualDataList.get(j).humidity);
                Log.d(TAG,"Actual Data List Pressure : "+ actualDataList.get(j).pressure);
                Log.d(TAG,"Actual Data List Current Temp : "+ actualDataList.get(j).currentTemp);
                Log.d(TAG,"Actual Data List Max Temp : "+ actualDataList.get(j).maxTemp);
                Log.d(TAG,"Actual Data List Min Temp : "+ actualDataList.get(j).minTemp);

            }
        }

        if (currentDataList != null) {
            currentDataList.clear();
            currentDataList.addAll(asyncresponse.currentDataList);
            for (int k = 0; k < asyncresponse.currentDataList.size(); k++) {

                Log.d(TAG, "Current Data List City : " + currentDataList.get(k).cityName);
                Log.d(TAG, "Current Data WeatherBase : " + currentDataList.get(k).weatherBase);
                Log.d(TAG, "Current Data WeatherDate : "+ currentDataList.get(k).weatherDate);
                Log.d(TAG, "Current Data WeatherID : "+ currentDataList.get(k).weatherID);

            }

        }
        if (weatherDataList != null) {
            weatherDataList.clear();
            weatherDataList.addAll(asyncresponse.weatherDataList);
            for (int l = 0; l < asyncresponse.weatherDataList.size(); l++) {

                Log.d(TAG, "Weather Data List Weather Desc : " + weatherDataList.get(l).weatherDescription);
                Log.d(TAG, "Weather Data List Weather Condition : " + weatherDataList.get(l).weatherCondition);
                Log.d(TAG, "Weather Data List Weather Icon : "+ weatherDataList.get(l).weatherIcon);
                Log.d(TAG, "Weather Data List Weather ICON ID : "+ weatherDataList.get(l).iconID);

            }

        }
        if (windDataList != null) {
            windDataList.clear();
            windDataList.addAll(asyncresponse.windDataList);
            for (int i1 = 0; i1 < asyncresponse.windDataList.size(); i1++) {

                Log.d(TAG, "Wind Data List WindSpeed : " + windDataList.get(i1).windSpeed);
                Log.d(TAG, "Wind Data List WindDegree : " + windDataList.get(i1).windDegree);

            }

        }
        if (sysDataList != null) {
            sysDataList.clear();
            sysDataList.addAll(asyncresponse.sysDataList);
            DateFormat dateformat = DateFormat.getDateTimeInstance();
            for (int j1 = 0; j1 < asyncresponse.sysDataList.size(); j1++) {
                String sunrise = dateformat.format(new Date(sysDataList.get(j1).sunrise));
                String sunset = dateformat.format(new Date(sysDataList.get(j1).sunset));

                Log.d(TAG, "Other Data List Sunrise : " + sunrise);
                Log.d(TAG, "Other Data List Sunset : " + sunset);
                Log.d(TAG, "Other Data List Country : " + sysDataList.get(j1).country);
                Log.d(TAG, "Other Data List Message : " + sysDataList.get(j1).message);
                Log.d(TAG, "Other Data List Type : " + sysDataList.get(j1).type);
                Log.d(TAG, "Other Data List ID : " + sysDataList.get(j1).id);

            }

        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.calldispatch:
                CoreApplication.getInstance().getRequestDispatch().requestCurrentWeatherData(false, "20770");
                break;

            case R.id.itunesdispatch:

                Log.d(TAG,"Start of the Request");
                CoreApplication.getInstance().getRequestDispatch().requestItunesData("all", "100", false);
                break;

        }

    }

}

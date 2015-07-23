package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mike.givemewingzz.activeforecast.UI.CoreApplication;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service.NetworkService;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service.RequestObject;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

public class RequestDispatch
{

    public static final String CURRENT_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    public static final String FORECAST_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    public static final String HOURLY_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    public static final String TAG = RequestDispatch.class.getSimpleName();

    public RequestDispatch()
    {
        Log.d(TAG, "Inside Request Dispatch");
    }

    public void requestCurrentWeatherData(boolean flag, String zipCode)
    {
        Log.d(TAG, "Request Current Weather Data");
        Bundle authbundle = new Bundle();
        authbundle.putString("zip", zipCode);
        RequestObject requestObject = new RequestObject(ApplicationUtils.RequestType.CURRENT_WEATHER_DATA);
        requestObject.setBASE_URL(CURRENT_WEATHER_BASE_URL);
        requestObject.setREQUEST_TYPE(RequestObject.METHOD_GET);
        requestObject.setShouldCancelRequest(flag);
        requestObject.setRequestBundle(authbundle);

        Intent networkService = new Intent(CoreApplication.context, NetworkService.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(ApplicationUtils.IntentKey.REQUEST_KEY, requestObject);

        networkService.putExtras(bundle);
        CoreApplication.context.startService(networkService);
    }

}

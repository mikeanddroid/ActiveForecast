package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.backgroundprocess.AsyncRunnable;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.backgroundprocess.CustomAsyncTask;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.exceptions.ConnectionException;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.exceptions.NetworkServiceException;
import com.mike.givemewingzz.activeforecast.servermapping.ActualData;
import com.mike.givemewingzz.activeforecast.servermapping.Coordinates;
import com.mike.givemewingzz.activeforecast.servermapping.CurrentWeather;
import com.mike.givemewingzz.activeforecast.servermapping.OtherData;
import com.mike.givemewingzz.activeforecast.servermapping.Weather;
import com.mike.givemewingzz.activeforecast.servermapping.WeatherModel;
import com.mike.givemewingzz.activeforecast.servermapping.WindData;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class NetworkService extends Service implements ServiceTimeoutTimer.ServiceCancelBroadcaster {

    public static final String TAG = NetworkService.class.getSimpleName();

    private ThreadTaskQueue networkRequestQueue;
    private ThreadTaskQueue networkResponseQueue;

    /**
     * TimerTask interface instance for canceling service.
     *
     * @since 1.0
     */
    private ServiceTimeoutTimer currentServiceTimeout;
    private DecimalFormat format;

    /**
     * Time representing last call into this service.
     *
     * @since 1.0
     */
    private long serviceTimeMilliseconds;

    private long serviceTimeOut = ServiceTimeoutTimer.SERVICE_TWO_MINUTES_TIMEOUT;

    public int onStartCommand(Intent intent, int flag, int startId) {

        if (intent == null)
            return START_NOT_STICKY;

        Log.d(TAG, "Inside On Start Command");

        serviceTimeMilliseconds = System.currentTimeMillis();

        try {
            if (!ServiceTimeoutTimer.isAllowableValue(serviceTimeOut)) {
                serviceTimeOut = ServiceTimeoutTimer.SERVICE_TWO_MINUTES_TIMEOUT;
            }
            if (currentServiceTimeout == null) {
                currentServiceTimeout = new ServiceTimeoutTimer(this, null, startId, serviceTimeOut, serviceTimeMilliseconds);
            } else {
                currentServiceTimeout.cancel();
                currentServiceTimeout = new ServiceTimeoutTimer(this, null, startId, serviceTimeOut, serviceTimeMilliseconds);
            }
        } catch (Exception e) {
        }

        Bundle intentBundle = intent.getExtras();
        if (intentBundle == null)
            return START_NOT_STICKY;
        RequestObject requestObject = null;
        try {
            requestObject = intentBundle.getParcelable(ApplicationUtils.IntentKey.REQUEST_KEY);
            processRequest(requestObject);
        } catch (Exception e) {
            Log.e(TAG, "Request Not Completed", e);
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private synchronized void processRequest(RequestObject requestObject) {

        final int requestIndentifier = requestObject.getDATA_TYPE_ID();

        NetworkThread currentThread;
        if (networkRequestQueue == null) {
            networkRequestQueue = new ThreadTaskQueue();
        }

        try {
            currentThread = new NetworkThread(this, ApplicationUtils.getReceiverForType(requestIndentifier), requestObject);
            networkRequestQueue.executeThread(currentThread);

        } catch (ConnectionException e) {
            Log.e(TAG, "Connection failed. Starting debug.");

            ResponseObject responseObject = new ResponseObject(requestObject);
            responseObject.setCode(ResponseObject.FAILURE);
            ErrorObject errObj = new ErrorObject(0);
            errObj.setLogMessage("Network Connection Unavailable  " + requestObject);
            errObj.setErrorCode(408);//Error code for Not Found or use 408 for timeout
            responseObject.setErrorObject(errObj);
            handleNetworkResponse(responseObject);
        } catch (NetworkServiceException nEx) {
            ResponseObject responseObject = new ResponseObject(requestObject);
            responseObject.setCode(ResponseObject.FAILURE);
            ErrorObject errObj = new ErrorObject(0);
            errObj.setLogMessage("Background Network Failure" + requestObject);
            errObj.setErrorCode(001);//Error code for Not Found or use 408 for timeout
            responseObject.setErrorObject(errObj);
            handleNetworkResponse(responseObject);
        }

    }

    public void handleNetworkResponse(ResponseObject responseobject) {

        Log.i(TAG, "handleNetworkResponse");
        if (networkResponseQueue == null) {
            networkResponseQueue = new ThreadTaskQueue();
        }

        ResultsAsyncTask resultsasynctask = new ResultsAsyncTask(networkResponseQueue, responseobject.copyObject());
        AsyncRunnable asyncrunnable = new AsyncRunnable(this, networkResponseQueue, resultsasynctask);
        asyncrunnable.setName(responseobject.getOriginalRequestObject().getSessionIdentifier());
        resultsasynctask.setAsyncRunnable(asyncrunnable);
        networkResponseQueue.executeThread(asyncrunnable);

    }


    public void processResponse(ResponseObject responseobject) {

        Log.i(TAG, "Inside Process Response");

        if (responseobject != null) {

            if (responseobject.getOriginalRequestObject() != null) {

                final int responseType = responseobject.getOriginalRequestObject().getDATA_TYPE_ID();

                switch (responseType) {

                    case ApplicationUtils.RequestType.CURRENT_WEATHER_DATA:

                        Log.i(TAG, "Inside CURRENT_WEATHER_DATA Response");

                        processCurrentData(responseobject);

                        break;
                    case ApplicationUtils.RequestType.FORECAST_WEATHER_DATA:

                        Log.i(TAG, "Inside FORECAST_WEATHER_DATA Response");

                        break;
                    case ApplicationUtils.RequestType.HISTORIC_WEATHER_DATA:

                        Log.i(TAG, "Inside HISTORIC_WEATHER_DATA Response");

                        break;
                    case ApplicationUtils.RequestType.HOURLY_WEATHER_DATA:

                        Log.i(TAG, "Inside HOURLY_WEATHER_DATA Response");

                        break;

                    default:
                        Log.i(TAG, "processResponse default: ");
                        break;

                }

            } else {

                Log.e(TAG, "processResponse requestObject is null: ");
            }

        } else {
            Log.e(TAG, "processResponse responseObject is null: ");
        }

    }

    public void processCurrentData(ResponseObject responseobject) {
        Log.i(TAG, (new StringBuilder()).append("Process Current Data ").append(responseobject).toString());
        if (responseobject != null) {

            boolean isSuccess = false;

            format = new DecimalFormat();
            format.setDecimalSeparatorAlwaysShown(false);

            if (responseobject.getCode() == ResponseObject.SUCCESS) {

                try {

                    JSONObject actualWeatherJson = responseobject.getJsonObject().getJSONObject("main");
                    JSONObject coordinatesJson = responseobject.getJsonObject().getJSONObject("coord");
                    JSONObject windJson = responseobject.getJsonObject().getJSONObject("wind");
                    JSONObject sysJson = responseobject.getJsonObject().getJSONObject("sys");

                    JSONArray weatherJsonArray = responseobject.getJsonObject().getJSONArray("weather");

                    JSONObject currentWeatherJsonObject = responseobject.getJsonObject();

                    List<WeatherModel> currentWeatherJson = CurrentWeather.createFromJSONObject(CurrentWeather.class, currentWeatherJsonObject);

                    List<WeatherModel> actualListWeatherJson = ActualData.createFromJSONObject(ActualData.class, actualWeatherJson);
                    List<WeatherModel> coordinatesListWeatherJson = Coordinates.createFromJSONObject(Coordinates.class, coordinatesJson);
                    List<WeatherModel> windWeatherListJson = WindData.createFromJSONObject(WindData.class, windJson);
                    List<WeatherModel> sysWeatherListJson = OtherData.createFromJSONObject(OtherData.class, sysJson);
                    List<WeatherModel> weatherListWeatherJson = Weather.createFromJSONList(Weather.class, weatherJsonArray);

                    Log.d(TAG, "Beginning ActualData transaction");
                    ActiveAndroid.beginTransaction();

                    new Delete().from(ActualData.class).execute();
                    new Delete().from(Coordinates.class).execute();
                    new Delete().from(OtherData.class).execute();
                    new Delete().from(WindData.class).execute();
                    new Delete().from(Weather.class).execute();
                    new Delete().from(CurrentWeather.class).execute();

                    for (int i = 0; i < actualListWeatherJson.size(); i++) {

                        ActualData actualWeather = (ActualData) actualListWeatherJson.get(i);

                        String s = (new StringBuilder()).append(Math.round(Double.valueOf(actualWeather.currentTemp).doubleValue() - 273.14999999999998D)).append("\260").append("C").toString();
                        String s1 = (new StringBuilder()).append(Math.round((Double.valueOf(actualWeather.currentTemp).doubleValue() * 9D) / 5D - 459.67000000000002D)).append("\260").append("F").toString();
                        actualWeather.currentTempInCelsius = s;
                        actualWeather.currentTempInFarenheit = s1;

                        actualWeather.save();

                    }
                    for (int i = 0; i < coordinatesListWeatherJson.size(); i++) {

                        Coordinates coordinates = (Coordinates) coordinatesListWeatherJson.get(i);

                        coordinates.save();

                    }
                    for (int i = 0; i < windWeatherListJson.size(); i++) {

                        WindData windData = (WindData) windWeatherListJson.get(i);

                        windData.save();

                    }
                    for (int i = 0; i < sysWeatherListJson.size(); i++) {

                        OtherData otherData = (OtherData) sysWeatherListJson.get(i);

                        otherData.sunrise = otherData.sunrise * 1000L;
                        otherData.sunset = otherData.sunset * 1000L;

                        otherData.save();

                    }
                    for (int i = 0; i < weatherListWeatherJson.size(); i++) {

                        Weather weather = (Weather) weatherListWeatherJson.get(i);

                        weather.save();

                    }
                    for (int i = 0; i < currentWeatherJson.size(); i++) {

                        CurrentWeather currentWeather = (CurrentWeather) currentWeatherJson.get(i);

                        currentWeather.save();

                    }

                    ActiveAndroid.setTransactionSuccessful();
                    Log.d(TAG, "CurrentWeather transaction Success");

                    isSuccess = true;

                } catch (Exception e) {
                    isSuccess = false;
                    Log.e(TAG, "processBestLists failed " + e, e);
                } finally {
                    ActiveAndroid.endTransaction();
                    sendLocalBroadcast(isSuccess, responseobject.getOriginalRequestObject().getBROADCAST_ACTION(), null);
                }

            } else {
                sendLocalBroadcast(isSuccess, responseobject.getOriginalRequestObject().getBROADCAST_ACTION(), null);
            }

        }

    }

    private void sendLocalBroadcast(boolean flag, String s, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(s);
        intent.putExtra("REQUEST_SUCCESS_KEY", flag);
        intent.putExtra("KEY_DATA", bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void sendCancelBroadcast(String action, int taskType, long timeInitiatied) {
        if (this.serviceTimeMilliseconds == timeInitiatied) {
            stopSelf();
        }
    }

    class ResultsAsyncTask extends CustomAsyncTask {

        private ResponseObject responseObject;

        public ResultsAsyncTask(ThreadTaskQueue asyncTaskQueueManager, ResponseObject responseObject) {
            super(asyncTaskQueueManager);
            this.responseObject = responseObject;

        }

        @Override
        protected Bundle doInBackground(Object... arg0) {
            Bundle returnBundle = new Bundle();
            try {
                Thread.sleep(5);
                processResponse(responseObject);
                Thread.sleep(2);
            } catch (InterruptedException interruptedException) {
            }

            return returnBundle;
        }
    }

}

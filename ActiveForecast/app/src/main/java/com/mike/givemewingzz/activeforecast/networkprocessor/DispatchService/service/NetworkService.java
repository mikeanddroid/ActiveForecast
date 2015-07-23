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
import com.mike.givemewingzz.activeforecast.servermapping.OtherData;
import com.mike.givemewingzz.activeforecast.servermapping.Weather;
import com.mike.givemewingzz.activeforecast.servermapping.WindData;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class NetworkService extends Service implements ServiceTimeoutTimer.ServiceCancelBroadcaster {

    public static final String TAG = NetworkService.class.getSimpleName();
    /**
     * TimerTask interface instance for canceling service.
     *
     * @since 1.0
     */
    private ServiceTimeoutTimer currentServiceTimeout;
    private DecimalFormat format;
    private ThreadTaskQueue networkRequestQueue;
    private ThreadTaskQueue networkResponseQueue;
    /**
     * Time representing last call into this service.
     *
     * @since 1.0
     */
    private long serviceTimeMilliseconds;

    private long serviceTimeOut = 120000L;
    ;

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

        Log.i(TAG, "handleNetworkResponse   ");
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

    public void processCurrentData(ResponseObject responseObject) {
        Log.i(TAG, (new StringBuilder()).append("Process Current Data ").append(responseObject).toString());
        if (responseObject != null) {

            boolean isSuccess = false;

            format = new DecimalFormat();
            format.setDecimalSeparatorAlwaysShown(false);

            if (responseObject.getCode() == ResponseObject.SUCCESS) {

                try {




                } catch (Exception e) {
                    isSuccess = false;
                    Log.e(TAG, "processBestLists failed " + e, e);
                } finally {
                    ActiveAndroid.endTransaction();
                    sendLocalBroadcast(isSuccess, responseObject.getOriginalRequestObject().getBROADCAST_ACTION(), null);
                }

            }

        }
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        obj = responseobject.getJsonObject().getJSONObject("main");
        obj2 = responseobject.getJsonObject().getJSONObject("coord");
        obj4 = responseobject.getJsonObject().getJSONObject("wind");
        obj3 = responseobject.getJsonObject().getJSONObject("sys");
        obj5 = responseobject.getJsonObject().getJSONArray("weather");
        obj = ActualData.createFromJSONObject(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / ActualData, ((JSONObject) (obj)));
        obj2 = Coordinates.createFromJSONObject(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / Coordinates, ((JSONObject) (obj2)));
        obj3 = OtherData.createFromJSONObject(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / OtherData, ((JSONObject) (obj3)));
        obj4 = WindData.createFromJSONObject(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / WindData, ((JSONObject) (obj4)));
        obj5 = Weather.createFromJSONList(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / Weather, ((org.json.JSONArray) (obj5)));
        Log.d(TAG, "Beginning ActualData transaction");
        ActiveAndroid.beginTransaction();
        (new Delete()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / ActualData).execute();
        (new Delete()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / Coordinates).execute();
        (new Delete()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / OtherData).execute();
        (new Delete()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / WindData).execute();
        (new Delete()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / Weather).execute();
        int i = 0;
        _L6:
        if (i >= ((List) (obj)).size()) {
            break; /* Loop/switch isn't completed */
        }
        ActualData actualdata = (ActualData) ((List) (obj)).get(i);
        String s = (new StringBuilder()).append(Math.round(Double.valueOf(actualdata.currentTemp).doubleValue() - 273.14999999999998D)).append("\260").append("C").toString();
        String s1 = (new StringBuilder()).append(Math.round((Double.valueOf(actualdata.currentTemp).doubleValue() * 9D) / 5D - 459.67000000000002D)).append("\260").append("F").toString();
        actualdata.currentTempInCelsius = s;
        actualdata.currentTempInFarenheit = s1;
        actualdata.save();
        i++;
        if (true)goto _L6;else goto _L5
        _L9:
        if (i >= ((List) (obj2)).size())goto _L8;else goto _L7
        _L7:
        ((Coordinates) ((List) (obj2)).get(i)).save();
        i++;
        goto _L9
        _L10:
        if (i >= ((List) (obj3)).size()) {
            break MISSING_BLOCK_LABEL_689;
        }
        OtherData otherdata = (OtherData) ((List) (obj3)).get(i);
        otherdata.sunrise = otherdata.sunrise * 1000L;
        otherdata.sunset = otherdata.sunset * 1000L;
        otherdata.save();
        i++;
        goto _L10
        _L11:
        if (i >= ((List) (obj4)).size()) {
            break MISSING_BLOCK_LABEL_695;
        }
        ((WindData) ((List) (obj4)).get(i)).save();
        i++;
        goto _L11
        _L12:
        Object obj1;
        for (; i >= ((List) (obj5)).size(); i = 0) {
            break MISSING_BLOCK_LABEL_585;
        }

        ((Weather) ((List) (obj5)).get(i)).save();
        i++;
        goto _L12
        ActiveAndroid.setTransactionSuccessful();
        Log.d(TAG, "CurrentWeather transaction Success");
        ActiveAndroid.endTransaction();
        sendLocalBroadcast(true, responseobject.getOriginalRequestObject().getBROADCAST_ACTION(), null);
        _L2:
        return;
        obj1;
        Log.e(TAG, "Process Current Data failed ", ((Throwable) (obj1)));
        ActiveAndroid.endTransaction();
        sendLocalBroadcast(false, responseobject.getOriginalRequestObject().getBROADCAST_ACTION(), null);
        return;
        obj1;
        ActiveAndroid.endTransaction();
        sendLocalBroadcast(false, responseobject.getOriginalRequestObject().getBROADCAST_ACTION(), null);
        throw obj1;
        _L4:
        sendLocalBroadcast(false, responseobject.getOriginalRequestObject().getBROADCAST_ACTION(), null);
        return;
        _L5:
        i = 0;
        goto _L9
        _L8:
        i = 0;
        goto _L10
                i = 0;
        goto _L11
    }

    private void sendLocalBroadcast(boolean flag, String s, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(s);
        intent.putExtra("REQUEST_SUCCESS_KEY", flag);
        intent.putExtra("KEY_DATA", bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void sendCancelBroadcast(String s, int i, long l) {
        if (serviceTimeMilliseconds == l) {
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

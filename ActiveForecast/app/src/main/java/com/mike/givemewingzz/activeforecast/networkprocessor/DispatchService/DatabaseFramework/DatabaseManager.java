package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.DatabaseFramework;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.mike.givemewingzz.activeforecast.servermapping.ActualData;
import com.mike.givemewingzz.activeforecast.servermapping.Coordinates;
import com.mike.givemewingzz.activeforecast.servermapping.CurrentWeather;
import com.mike.givemewingzz.activeforecast.servermapping.OtherData;
import com.mike.givemewingzz.activeforecast.servermapping.Weather;
import com.mike.givemewingzz.activeforecast.servermapping.WindData;
import com.mike.givemewingzz.activeforecast.servermapping.WrapperClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to consolidate database cursor access.  Calls for cursor data should come through
 * this class which should only be accessed via the base application.
 */
public class DatabaseManager {

    private static final String TAG = DatabaseManager.class.getSimpleName();
    private static DatabaseManager instance;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Base cursor method which will return all model objects for the provided model class.
     *
     * @param modelClass - class to retrieve models from
     * @return - a cursor to the model data
     */
    public Cursor fetchGenericCursor(Class<? extends Model> modelClass) {

        String tableName = getTableName(modelClass);

        String query = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(modelClass).toSql();

        return executeQueryForCursor(query);
    }


    public Cursor fetchActualDataCursor() {
        String tableName = getTableName(ActualData.class);

        String query = new Select(tableName + ".*, " + tableName + ".Id as _id").from(ActualData.class).toSql();

        // Execute query on the underlying ActiveAndroid SQLite database
        return Cache.openDatabase().rawQuery(query, null);
    }

    public AsyncResponseTask fetchCurrentWeather(AsyncResponseListener asyncresponselistener) {

        Log.d(TAG, "Inside fetchCurrentWeather");

        From actualFrom = new Select().from(ActualData.class);
        From currentFrom = new Select().from(CurrentWeather.class);
        From weatherFrom = new Select().from(Weather.class);
        From windFrom = new Select().from(WindData.class);
        From dataFrom = new Select().from(OtherData.class);
        From coordinatesFrom = new Select().from(Coordinates.class);

        AsyncResponseTask asyncTask = new AsyncResponseTask(asyncresponselistener);
        asyncTask.execute(actualFrom, currentFrom, weatherFrom, windFrom, dataFrom, coordinatesFrom);

        return asyncTask;
    }

    public class AsyncResponseTask extends AsyncTask<From, Integer, AsyncResponse> {

        private AsyncResponseListener asyncResponseListener;

        public AsyncResponseTask(AsyncResponseListener asyncresponselistener) {
            asyncResponseListener = asyncresponselistener;
        }

        @Override
        protected AsyncResponse doInBackground(From... params) {

            List<Model> actualList = new ArrayList<>();
            List<Model> currentList = new ArrayList<>();
            List<Model> sysList = new ArrayList<>();
            List<Model> weatherList = new ArrayList<>();
            List<Model> windList = new ArrayList<>();
            List<Model> coordinatesList = new ArrayList<>();

            List<ActualData> actualDataList = new ArrayList<>();
            List<CurrentWeather> currentDataList = new ArrayList<>();
            List<Weather> weatherDataList = new ArrayList<>();
            List<WindData> windDataList = new ArrayList<>();
            List<OtherData> sysDataList = new ArrayList<>();
            List<Coordinates> coordinatesDataList = new ArrayList<>();

            actualList.addAll(params[0].execute());
            currentList.addAll(params[1].execute());
            weatherList.addAll(params[2].execute());
            windList.addAll(params[3].execute());
            sysList.addAll(params[4].execute());
            coordinatesList.addAll(params[5].execute());

            List<WrapperClass> completeList = new ArrayList<>();

            try {

                for (Model model : actualList) {

                    WrapperClass wrapperClass = new WrapperClass(model, WrapperClass.ACTUAL_DATA_WEATHER);

                    completeList.add(wrapperClass);

                    actualDataList.add((ActualData) model);
                }

                for (Model model : currentList) {

                    WrapperClass wrapperClass = new WrapperClass(model, WrapperClass.CURRENT_DATA_WEATHER);

                    completeList.add(wrapperClass);

                    currentDataList.add((CurrentWeather) model);
                }

                for (Model model : sysList) {

                    WrapperClass wrapperClass = new WrapperClass(model, WrapperClass.OTHER_DATA_WEATHER);

                    completeList.add(wrapperClass);

                    sysDataList.add((OtherData) model);
                }

                for (Model model : weatherList) {

                    WrapperClass wrapperClass = new WrapperClass(model, WrapperClass.WEATHER_DATA_WEATHER);

                    completeList.add(wrapperClass);

                    weatherDataList.add((Weather) model);
                }

                for (Model model : windList) {

                    WrapperClass wrapperClass = new WrapperClass(model, WrapperClass.WIND_DATA_WEATHER);

                    completeList.add(wrapperClass);

                    windDataList.add((WindData) model);
                }

                for (Model model : coordinatesList) {

                    WrapperClass wrapperClass = new WrapperClass(model, WrapperClass.COORDINATES_DATA_WEATHER);

                    completeList.add(wrapperClass);

                    coordinatesDataList.add((Coordinates) model);
                }

            } catch (Exception e) {
                Log.e(TAG, "Array out of bounds.", e);
            }

            return new AsyncResponse(completeList, actualDataList, currentDataList, sysDataList, weatherDataList, windDataList, coordinatesDataList);

        }

        @Override
        protected void onPostExecute(AsyncResponse asyncresponse) {
            asyncResponseListener.onRequestComplete(asyncresponse);
        }

    }

    public interface AsyncResponseListener {

        void onRequestComplete(AsyncResponse asyncresponse);
    }

    public class AsyncResponse {

        public List<ActualData> actualDataList;
        public List<CurrentWeather> currentDataList;
        public List<OtherData> sysDataList;
        public List<Weather> weatherDataList;
        public List<WindData> windDataList;
        public List<Coordinates> coordinatesDataList;

        public List<WrapperClass> completeList;

        public AsyncResponse(
                List<WrapperClass> completeList,
                List<ActualData> actualDataList,
                List<CurrentWeather> currentDataList,
                List<OtherData> sysDataList,
                List<Weather> weatherDataList,
                List<WindData> windDataList,
                List<Coordinates> coordinatesDataList) {

            this.completeList = completeList;
            this.actualDataList = actualDataList;
            this.currentDataList = currentDataList;
            this.sysDataList = sysDataList;
            this.weatherDataList = weatherDataList;
            this.windDataList = windDataList;
            this.coordinatesDataList = coordinatesDataList;

        }
    }

    private Cursor executeQueryForCursor(String s) {
        return Cache.openDatabase().rawQuery(s, null);
    }


    private String getTableName(Class class1) {
        return Cache.getTableInfo(class1).getTableName();
    }

}

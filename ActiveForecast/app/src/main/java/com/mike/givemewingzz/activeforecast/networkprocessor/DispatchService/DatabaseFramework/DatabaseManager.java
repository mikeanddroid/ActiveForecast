package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.DatabaseFramework;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.mike.givemewingzz.activeforecast.servermapping.ActualData;
import com.mike.givemewingzz.activeforecast.servermapping.CurrentWeather;
import com.mike.givemewingzz.activeforecast.servermapping.OtherData;
import com.mike.givemewingzz.activeforecast.servermapping.Weather;
import com.mike.givemewingzz.activeforecast.servermapping.WindData;
import com.mike.givemewingzz.activeforecast.servermapping.WrapperClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseManager {
    public class AsyncResponse {

        public List actualDataList;
        public List currentDataList;
        public List responseData;
        public List sysDataList;
        final DatabaseManager this$0;
        public List weatherDataList;
        public List windDataList;

        public AsyncResponse(List list, List list1, List list2, List list3, List list4, List list5) {
            this$0 = DatabaseManager.this;
            super();
            responseData = list;
            actualDataList = list1;
            currentDataList = list2;
            weatherDataList = list3;
            windDataList = list4;
            sysDataList = list5;
        }
    }

    public static interface AsyncResponseListener {

        public abstract void onRequestComplete(AsyncResponse asyncresponse);
    }

    public class AsyncResponseTask extends AsyncTask {

        private AsyncResponseListener asyncResponseListener;
        final DatabaseManager this$0;

        protected transient AsyncResponse doInBackground(From afrom[]) {
            ArrayList arraylist;
            ArrayList arraylist1;
            ArrayList arraylist2;
            ArrayList arraylist3;
            ArrayList arraylist4;
            Object obj;
            Object obj1;
            Object obj2;
            Object obj3;
            Object obj4;
            obj4 = new ArrayList();
            obj3 = new ArrayList();
            obj2 = new ArrayList();
            obj1 = new ArrayList();
            obj = new ArrayList();
            ((List) (obj4)).addAll(afrom[0].execute());
            ((List) (obj3)).addAll(afrom[1].execute());
            ((List) (obj2)).addAll(afrom[2].execute());
            ((List) (obj1)).addAll(afrom[3].execute());
            ((List) (obj)).addAll(afrom[4].execute());
            afrom = new ArrayList();
            arraylist = new ArrayList();
            arraylist1 = new ArrayList();
            arraylist2 = new ArrayList();
            arraylist3 = new ArrayList();
            arraylist4 = new ArrayList();
            Model model4;
            for (obj4 = ((List) (obj4)).iterator(); ((Iterator) (obj4)).hasNext(); afrom.add((ActualData) model4)) {
                model4 = (Model) ((Iterator) (obj4)).next();
                arraylist4.add(new WrapperClass(model4, 1));
            }

            goto _L1
            _L3:
            return new AsyncResponse(arraylist4, afrom, arraylist, arraylist1, arraylist2, arraylist3);
            _L1:
            Model model3;
            for (obj3 = ((List) (obj3)).iterator(); ((Iterator) (obj3)).hasNext(); arraylist.add((CurrentWeather) model3)) {
                model3 = (Model) ((Iterator) (obj3)).next();
                arraylist4.add(new WrapperClass(model3, 2));
            }

            Model model2;
            for (obj2 = ((List) (obj2)).iterator(); ((Iterator) (obj2)).hasNext(); arraylist1.add((Weather) model2)) {
                model2 = (Model) ((Iterator) (obj2)).next();
                arraylist4.add(new WrapperClass(model2, 3));
            }

            Model model1;
            for (obj1 = ((List) (obj1)).iterator(); ((Iterator) (obj1)).hasNext(); arraylist2.add((WindData) model1)) {
                model1 = (Model) ((Iterator) (obj1)).next();
                arraylist4.add(new WrapperClass(model1, 4));
            }

            try {
                obj = ((List) (obj)).iterator();
                while (((Iterator) (obj)).hasNext()) {
                    Model model = (Model) ((Iterator) (obj)).next();
                    arraylist4.add(new WrapperClass(model, 5));
                    arraylist3.add((OtherData) model);
                }
            }
            // Misplaced declaration of an exception variable
            catch (Object obj) {
                Log.e(DatabaseManager.TAG, "Error setting the list.", ((Throwable) (obj)));
            }
            if (true)goto _L3;else goto _L2
            _L2:
        }

        protected volatile Object doInBackground(Object aobj[]) {
            return doInBackground((From[]) aobj);
        }

        protected void onPostExecute(AsyncResponse asyncresponse) {
            asyncResponseListener.onRequestComplete(asyncresponse);
        }

        protected volatile void onPostExecute(Object obj) {
            onPostExecute((AsyncResponse) obj);
        }

        public AsyncResponseTask(AsyncResponseListener asyncresponselistener) {
            this$0 = DatabaseManager.this;
            super();
            asyncResponseListener = asyncresponselistener;
        }
    }


    private static final String TAG = com / example / givemewingz / activeforecast / mike / services / DatabaseManager.getSimpleName();
    private static DatabaseManager instance;

    private DatabaseManager() {
    }

    private Cursor executeQueryForCursor(String s) {
        return Cache.openDatabase().rawQuery(s, null);
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private String getTableName(Class class1) {
        return Cache.getTableInfo(class1).getTableName();
    }

    public ArrayList addListOf(List list) {
        return new ArrayList();
    }

    public Cursor fetchActualDataCursor() {
        String s = getTableName(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / ActualData);
        s = (new Select(new String[]{
                (new StringBuilder()).append(s).append(".*, ").append(s).append(".Id as _id").toString()
        })).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / ActualData).toSql();
        return Cache.openDatabase().rawQuery(s, null);
    }

    public AsyncResponseTask fetchCurrentWeather(AsyncResponseListener asyncresponselistener) {
        Log.d(TAG, "Inside fetchCurrentWeather");
        getTableName(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / CurrentWeather);
        getTableName(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / ActualData);
        getTableName(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / Weather);
        getTableName(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / WindData);
        getTableName(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / OtherData);
        getTableName(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / Coordinates);
        From from = (new Select()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / ActualData);
        From from1 = (new Select()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / CurrentWeather);
        From from2 = (new Select()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / Weather);
        From from3 = (new Select()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / WindData);
        From from4 = (new Select()).from(com / example / givemewingz / activeforecast / mike / application / servermapping / data / objects / currentweather / OtherData);
        asyncresponselistener = new AsyncResponseTask(asyncresponselistener);
        asyncresponselistener.execute(new From[]{
                from, from1, from2, from3, from4
        });
        return asyncresponselistener;
    }


}

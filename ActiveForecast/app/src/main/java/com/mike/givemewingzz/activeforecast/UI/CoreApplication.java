package com.mike.givemewingzz.activeforecast.UI;

import android.content.Context;

import com.activeandroid.app.Application;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.DatabaseFramework.DatabaseManager;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.RequestDispatch;

public class CoreApplication extends Application
{

    public static Context context;
    private static CoreApplication coreApplication;
    private ApplicationPreferences preferences;
    private RequestDispatch requestDispatch;

    public CoreApplication()
    {
    }

    public static CoreApplication getInstance()
    {
        return coreApplication;
    }

    public DatabaseManager getDBCursorManager()
    {
        return DatabaseManager.getInstance();
    }

    public ApplicationPreferences getPreferences()
    {
        return preferences;
    }

    public RequestDispatch getRequestDispatch()
    {
        this;
        JVM INSTR monitorenter ;
        RequestDispatch requestdispatch = requestDispatch;
        this;
        JVM INSTR monitorexit ;
        return requestdispatch;
        Exception exception;
        exception;
        throw exception;
    }

    public void onCreate()
    {
        super.onCreate();
        coreApplication = this;
        context = getApplicationContext();
        preferences = new ApplicationPreferences();
        requestDispatch = new RequestDispatch();
    }
}

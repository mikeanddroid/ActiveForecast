package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.backgroundprocess;

import android.os.AsyncTask;
import android.os.Bundle;

import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service.ThreadTaskQueue;

public abstract class CustomAsyncTask extends AsyncTask<Object, Bundle, Bundle> {

    protected AsyncRunnable asyncRunnable;
    protected ThreadTaskQueue asyncTaskQueueManager;
    volatile boolean publishCalled = false;
    public static final String SUCCESS = "SUCCESS";

    public CustomAsyncTask(ThreadTaskQueue asyncTaskQueueManager) {
        this.asyncTaskQueueManager = asyncTaskQueueManager;
    }

    public final void setAsyncRunnable(AsyncRunnable asyncRunnable) {
        this.asyncRunnable = asyncRunnable;
    }

    /**
     * @param objects
     * @return
     */
    @Override
    protected abstract Bundle doInBackground(Object... objects);

    @Override
    protected void onProgressUpdate(Bundle... bundles) {
        super.onProgressUpdate(bundles); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onPostExecute(Bundle result) {
        super.onPostExecute(result); //To change body of generated methods, choose Tools | Templates.
    }

}

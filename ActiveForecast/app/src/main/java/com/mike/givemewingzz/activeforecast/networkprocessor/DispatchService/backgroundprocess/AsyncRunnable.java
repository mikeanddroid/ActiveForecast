package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.backgroundprocess;

import android.content.Context;

import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service.ThreadTaskQueue;

public class AsyncRunnable implements Runnable {

    private Context context;
    private ThreadTaskQueue taskManager;
    private CustomAsyncTask customAsyncTask;
    private String name;

    public AsyncRunnable(Context context, ThreadTaskQueue threadTaskQueue, CustomAsyncTask customAsyncTask) {

        this.context = context;
        this.taskManager = threadTaskQueue;
        this.customAsyncTask = customAsyncTask;

    }

    @Override
    public void run() {
        this.customAsyncTask.execute();
    }

    public Context getContext(){
        return context;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}

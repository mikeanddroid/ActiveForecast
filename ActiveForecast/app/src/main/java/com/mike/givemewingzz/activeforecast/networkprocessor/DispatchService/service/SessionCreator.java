package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.os.SystemClock;

public class SessionCreator {

    public SessionCreator() {
    }

    public static String getElapsedSessionId(String sessionName) {
        if (sessionName == null) {
            return null;
        } else {
            return (new StringBuilder()).append(sessionName).append("_").append(SystemClock.elapsedRealtime()).toString();
        }
    }
}

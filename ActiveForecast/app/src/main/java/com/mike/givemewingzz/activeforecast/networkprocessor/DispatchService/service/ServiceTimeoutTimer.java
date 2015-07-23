package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import java.util.Timer;
import java.util.TimerTask;


public final class ServiceTimeoutTimer extends TimerTask {

    public static final String TAG = ServiceTimeoutTimer.class.getSimpleName();
    private String a;
    private int b;
    volatile boolean isCancelled;
    private ServiceTimeoutTimer.ServiceCancelBroadcaster c;
    public static final long TWELVE_SECONDS_TIMEOUT = 12000L;
    public static final long FIFTEEN_SECONDS_TIMEOUT = 15000L;
    public static final long SERVICE_TWO_MINUTES_TIMEOUT = 120000L;
    public static final long SERVICE_FIVE_MINUTES_TIMEOUT = 300000L;
    private long d;

    public ServiceTimeoutTimer(ServiceTimeoutTimer.ServiceCancelBroadcaster serviceCancelBroadcaster, String receiverAction, int taskType, long timeout, long serviceTimeMilliseconds) {
        this();
        this.a = receiverAction;
        this.b = taskType;
        this.c = serviceCancelBroadcaster;
        this.d = serviceTimeMilliseconds;
        Timer var8 = new Timer();
        var8.schedule(this, timeout);
    }

    private ServiceTimeoutTimer() {
        this.b = -1;
        this.isCancelled = false;
        this.d = -1L;
    }

    public void run() {
        if(!this.isCancelled && this.c != null) {
            this.c.sendCancelBroadcast(this.a, this.b, this.d);
        }

    }

    public boolean cancel() {
        this.isCancelled = true;
        return super.cancel();
    }

    public long getServiceTimeMilliseconds() {
        return this.d;
    }

    public void setServiceTimeMilliseconds(long serviceTimeMilliseconds) {
        this.d = serviceTimeMilliseconds;
    }

    public static boolean isAllowableValue(long timeOut) {
        boolean var2 = false;
        long[] var3 = new long[]{12000L, 15000L, 120000L, 300000L, -1L};

        for(int var4 = 0; var4 < var3.length; ++var4) {
            long var5 = var3[var4];
            if(var5 == timeOut) {
                var2 = true;
                break;
            }
        }

        return var2;
    }

    public interface ServiceCancelBroadcaster {
        void sendCancelBroadcast(String var1, int var2, long var3);
    }
}
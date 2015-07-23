package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.util.Log;

import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.backgroundprocess.AsyncRunnable;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTaskQueue {
    private static final String TAG = ThreadTaskQueue.class.getSimpleName();

    private PausableThreadPoolExecutor pausableThreadPoolExecutor;
    private LinkedBlockingQueue<Object> linkedBlockQueue;// = new L
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private static final int NUMBER_OF_CORES = 5;
    private final Map<String, Future> queueRequestFuturesHash = new ConcurrentHashMap<>();
    private final Map<String, Future> queueResponseFuturesHash = new ConcurrentHashMap<>();

    public ThreadTaskQueue() {

        try {
            if (linkedBlockQueue == null) {
                linkedBlockQueue = new LinkedBlockingQueue<>(5);
            }
        } catch (Exception e) {
        }

        try {
            if (pausableThreadPoolExecutor == null) {
                pausableThreadPoolExecutor = new PausableThreadPoolExecutor(
                        NUMBER_OF_CORES, // Initial pool size
                        NUMBER_OF_CORES, // Max pool size
                        KEEP_ALIVE_TIME,
                        KEEP_ALIVE_TIME_UNIT,
                        linkedBlockQueue);

            }
        } catch (Exception e) {
        }
    }

    public void executeThread(NetworkThread runnable) {
        try {
            backToTheFuture();
        } catch (Exception e) {
            Log.e(TAG, ApplicationUtils.ApplicationConstants.LOG_TAG_SOMETHING_WENT_WRONG, e);
        }

        Future future = pausableThreadPoolExecutor.submit(runnable);
        queueRequestFuturesHash.put(runnable.getName(), future);

    }

    public void executeThread(ReceiverThread runnable) {

        try {
            backToTheFuture();
        } catch (Exception e) {
            Log.e(TAG, ApplicationUtils.ApplicationConstants.LOG_TAG_SOMETHING_WENT_WRONG, e);
        }

        Future future = pausableThreadPoolExecutor.submit(runnable);
        queueResponseFuturesHash.put((runnable).getName(), future);

    }


    public void executeThread(AsyncRunnable runnable) {

        try {
            backToTheFuture();
        } catch (Exception e) {
            Log.e(TAG, ApplicationUtils.ApplicationConstants.LOG_TAG_SOMETHING_WENT_WRONG, e);
        }

        Future future = pausableThreadPoolExecutor.submit(runnable);
        queueResponseFuturesHash.put((runnable).getName(), future);

    }

    /**
     * Clears futures that are no longer running with their get() returns null;
     */
    private synchronized void backToTheFuture() throws Exception {
        if (queueRequestFuturesHash != null) {
            Set<String> keySet = queueRequestFuturesHash.keySet();
            for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext(); ) {
                String key = iterator.next();
                Future f = queueRequestFuturesHash.get(key);
                if (f.isDone() || f.isCancelled()) {
                    queueRequestFuturesHash.remove(key);
                }
            }
        }
    }

    class PausableThreadPoolExecutor extends ThreadPoolExecutor {

        private boolean isPaused;
        private ReentrantLock pauseLock = new ReentrantLock();
        private Condition unpaused = pauseLock.newCondition();

        public PausableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, LinkedBlockingQueue workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected void beforeExecute(Thread thread, Runnable runnable) {
            super.beforeExecute(thread, runnable);
            pauseLock.lock();
            try {
                while (isPaused) {
                    unpaused.await();
                }
            } catch (InterruptedException ie) {
                thread.interrupt();
            } finally {
                pauseLock.unlock();
            }
        }

        public void pause() {
            pauseLock.lock();
            try {
                isPaused = true;
            } finally {
                pauseLock.unlock();
            }
        }

        public void resume() {
            pauseLock.lock();
            try {
                isPaused = false;
                unpaused.signalAll();
            } finally {
                pauseLock.unlock();
            }
        }
    }
}

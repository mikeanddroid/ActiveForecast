package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.exceptions;

/**
 * Created by GiveMeWingzz on 7/20/2015.
 */
public class NetworkServiceException extends Exception {

    public NetworkServiceException(String message) {
        super(message);
    }

    public NetworkServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NetworkServiceException(Throwable throwable) {
        super(throwable);
    }
}
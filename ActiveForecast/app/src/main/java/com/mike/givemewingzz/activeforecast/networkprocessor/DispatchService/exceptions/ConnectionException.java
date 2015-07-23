package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.exceptions;

/**
 * Created by GiveMeWingzz on 7/20/2015.
 */
public class ConnectionException extends NetworkServiceException {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConnectionException(Throwable throwable) {
        super(throwable);
    }
}
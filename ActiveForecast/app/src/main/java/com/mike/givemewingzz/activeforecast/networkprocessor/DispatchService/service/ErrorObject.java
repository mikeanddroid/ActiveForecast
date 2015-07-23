package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.os.Parcel;
import android.os.Parcelable;

public class ErrorObject implements Parcelable{

    public static final String TAG = "ErrorObject";

    /**
     * Response or similar code. Typically the HTTP Response Code.
     */
    private int errorCode;
    /**
     * Message that will go out to log;
     */
    private String logMessage;
    /**
     * UI Message text to display in the UI. This is not likely to be set at the Network layer but facilitates reuse of this object at the UI layer
     */
    private String uiMessage;
    /**
     * UI Title text to display in the UI. This is not likely to be set at the Network layer but facilitates reuse of this object at the UI layer
     */
    private String uiTitle;

    public ErrorObject(int errorCode) {
        this();
        this.errorCode = errorCode;
    }

    public ErrorObject( ) {
        super();
    }

    /**
     * Private constructor for Parcelable unmarshalling.
     *
     * @param in
     * @since 1.0
     */
    private ErrorObject(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(errorCode);
        dest.writeString(uiMessage);
        dest.writeString(logMessage);
        dest.writeString(uiTitle);
    }


    /**
     * Unmarshals data and assign to variables.
     *
     * @param in parcel holding data stored in <code>writeToParcel()</code>
     */
    private void readFromParcel(Parcel in) {
        errorCode = in.readInt();
        uiMessage = in.readString();
        logMessage = in.readString();
        uiTitle = in.readString();
    }
    /**
     *
     * This field is needed for Android to be able to create new objects,
     * individually or as arrays. This also means that you can use use the
     * default constructor to create the object and use another method to
     * hyrdate it as necessary.
     *
     * @since 1.0
     *
     */
    public static final Parcelable.Creator<ErrorObject> CREATOR = new Parcelable.Creator<ErrorObject>() {

        public ErrorObject createFromParcel(Parcel in) {
            return new ErrorObject(in);
        }

        public ErrorObject[] newArray(int size) {
            return new ErrorObject[size];
        }
    };


    /**
     * Accessor for value of the code defining this error.
     * @return errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Accessor for assigning errorCode of this object.
     * @param errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Accessor for value of the message to output to log.
     * @return logMessage
     */
    public String getLogMessage() {
        return logMessage;
    }

    /**
     * Accessor for assigning logMessage of this object.
     * @param logMessage
     */
    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }


    /**
     * Accessor for value of the UI message text.
     * @return uiMessage
     */
    public String getUiMessage() {
        return uiMessage;
    }

    /**
     * Accessor for assigning uiMessage of this object.
     * @param uiMessage
     */
    public void setUiMessage(String uiMessage) {
        this.uiMessage = uiMessage;
    }

    /**
     * Accessor for value of the UI title text.
     * @return uiTitle
     */
    public String getUiTitle() {
        return uiTitle;
    }

    /**
     * Accessor for assigning uiMessage of this object.
     * @param uiTitle
     */
    public void setUiTitle(String uiTitle) {
        this.uiTitle = uiTitle;
    }

    @Override
    public String toString() {
        return "ErrorObject{" +
                "errorCode=" + errorCode +
                ", logMessage='" + logMessage + '\'' +
                ", uiMessage='" + uiMessage + '\'' +
                ", uiTitle='" + uiTitle + '\'' +
                '}';
    }

    public ErrorObject copyObject(){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setErrorCode(errorCode);
        errorObject.setLogMessage(logMessage);
        errorObject.setUiMessage(uiMessage);
        errorObject.setUiTitle(uiTitle);
        return errorObject;
    }
}
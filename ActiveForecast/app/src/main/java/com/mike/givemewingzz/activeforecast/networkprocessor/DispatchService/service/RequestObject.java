package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.mike.givemewingzz.activeforecast.UI.CoreApplication;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

public class RequestObject implements Parcelable {


    public static final int METHOD_DELETE = 3;
    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;
    public static final int METHOD_PUT = 2;

    public static final String TAG = RequestObject.class.getSimpleName();
    private String BASE_URL;

    /**
     * Request Broadcast Action.
     */
    private String BROADCAST_ACTION;

    /**
     * Type of data object you want to request for.
     * E.g. CURRENT_WEATHER_FORECAST
     */
    private int DATA_TYPE_ID;

    /**
     * Request type: must be one of <code>METHOD_GET</code>, <code>METHOD_POST</code>, <code>METHOD_PUT</code>, <code>METHOD_DELETE</code>.
     */
    private int REQUEST_TYPE;

    /**
     * Hashmap for key value pair for request header data to the server.
     */
    private Bundle authBundle;

    /**
     * Hashmap for key value pair for request data to the server.
     */
    private Bundle requestBundle = null;

    /**
     * Each request is assigned a unique session key that allows for canceling  one specific request from a group that has the same identifier.
     */
    private String sessionIdentifier;


    private boolean shouldCancelRequest;

    public RequestObject() {
        super();
    }

    public RequestObject(int dataTypeID) {
        this();
        this.DATA_TYPE_ID = dataTypeID;
        BROADCAST_ACTION = ApplicationUtils.getReceiverForType(dataTypeID);
        sessionIdentifier = SessionCreator.getElapsedSessionId(BROADCAST_ACTION);
    }

    /**
     * @param parcelIn parcel holding data stored in <code>writeToParcel()</code>
     */
    private RequestObject(Parcel parcelIn) {
        REQUEST_TYPE = parcelIn.readInt();
        shouldCancelRequest = parcelIn.readInt() == 1;
        DATA_TYPE_ID = parcelIn.readInt();
        sessionIdentifier = parcelIn.readString();
        requestBundle = parcelIn.readBundle(CoreApplication.class.getClassLoader());
        authBundle = parcelIn.readBundle(CoreApplication.class.getClassLoader());
        BROADCAST_ACTION = parcelIn.readString();
        BASE_URL = parcelIn.readString();

    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(REQUEST_TYPE);
        parcel.writeInt(shouldCancelRequest ? 1 : 0);
        parcel.writeInt(DATA_TYPE_ID);
        parcel.writeString(sessionIdentifier);
        parcel.writeBundle(requestBundle);
        parcel.writeBundle(authBundle);
        parcel.writeString(BROADCAST_ACTION);
        parcel.writeString(BASE_URL);
    }

    public RequestObject copyObject() {
        RequestObject requestobject = new RequestObject();
        requestobject.setREQUEST_TYPE(REQUEST_TYPE);
        requestobject.setShouldCancelRequest(shouldCancelRequest);
        requestobject.setDATA_TYPE_ID(DATA_TYPE_ID);
        requestobject.setRequestBundle(requestBundle);
        requestobject.setAuthBundle(authBundle);
        requestobject.setBROADCAST_ACTION(BROADCAST_ACTION);
        requestobject.setBASE_URL(BASE_URL);
        requestobject.setSessionIdentifier(sessionIdentifier);
        return requestobject;
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
    public static final Parcelable.Creator<RequestObject> CREATOR = new Parcelable.Creator<RequestObject>() {

        public RequestObject createFromParcel(Parcel parcel) {
            return new RequestObject(parcel);
        }

        public RequestObject[] newArray(int size) {
            return new RequestObject[size];
        }

    };

    public static int getMethodDelete() {
        return 3;
    }

    public static int getMethodGet() {
        return 0;
    }

    public static int getMethodPost() {
        return 1;
    }

    public static int getMethodPut() {
        return 2;
    }

    public int describeContents() {
        return 0;
    }

    public Bundle getAuthBundle() {
        return authBundle;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getBROADCAST_ACTION() {
        return BROADCAST_ACTION;
    }

    public int getDATA_TYPE_ID() {
        return DATA_TYPE_ID;
    }

    public int getREQUEST_TYPE() {
        return REQUEST_TYPE;
    }

    public Bundle getRequestBundle() {
        return requestBundle;
    }

    public String getSessionIdentifier() {
        return sessionIdentifier;
    }

    public boolean isShouldCancelRequest() {
        return shouldCancelRequest;
    }

    public void setAuthBundle(Bundle bundle) {
        authBundle = bundle;
    }

    public void setBASE_URL(String s) {
        BASE_URL = s;
    }

    public void setBROADCAST_ACTION(String s) {
        BROADCAST_ACTION = s;
    }

    public void setDATA_TYPE_ID(int i) {
        DATA_TYPE_ID = i;
    }

    public void setREQUEST_TYPE(int i) {
        REQUEST_TYPE = i;
    }

    public void setRequestBundle(Bundle bundle) {
        requestBundle = bundle;
    }

    public void setSessionIdentifier(String s) {
        sessionIdentifier = s;
    }

    public void setShouldCancelRequest(boolean flag) {
        shouldCancelRequest = flag;
    }

    public String toString() {
        return (new StringBuilder())
                .append("RequestObject{methodType=").append(REQUEST_TYPE)
                .append(", isCancelAction=").append(shouldCancelRequest)
                .append(", identifier=").append(DATA_TYPE_ID)
                .append(", sessionIdentifier='").append(sessionIdentifier)
                .append('\'').append(", requestData=").append(requestBundle)
                .append(", AuthData=").append(authBundle)
                .append(", baseURL='").append(BASE_URL)
                .append('\'').append(", broadcastAction='")
                .append(BROADCAST_ACTION).append('\'').append('}')
                .toString();
    }

}

package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.mike.givemewingzz.activeforecast.UI.CoreApplication;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseObject implements Parcelable {

    public static final int FAILURE = 0;
    public static final int SUCCESS = 1;
    public static final String TAG = ResponseObject.class.getSimpleName();
    private final String CODE_RANGE_EXCP = "code must be between  "+FAILURE+" AND "+SUCCESS+ " inclusive";;
    private String broadcastAction;
    private int code;
    private ErrorObject errorObject;
    private JSONObject jsonObject;
    private RequestObject originalRequestObject;
    private String sessionIdentifier;

    public ResponseObject() {
        super();
    }

    private ResponseObject(Parcel parcel) {
        this();
        readFromParcel(parcel);
    }


    public ResponseObject(RequestObject requestobject) {
        this();
        originalRequestObject = requestobject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static int getFAILURE() {
        return 0;
    }

    public static int getSUCCESS() {
        return 1;
    }

    private void readFromParcel(Parcel parcel) {

        originalRequestObject = parcel.readParcelable(CoreApplication.class.getClassLoader());
        errorObject = (ErrorObject) parcel.readParcelable(CoreApplication.class.getClassLoader());
        code = parcel.readInt();
        String val = parcel.readString();

        try {
            jsonObject = val == null ? null : new JSONObject(val);
        } catch (JSONException e) {
            Log.e(TAG, ApplicationUtils.ApplicationConstants.LOG_TAG_SOMETHING_WENT_WRONG, e);
        }

    }

    public ResponseObject copyObject() {

        RequestObject requestObject = originalRequestObject != null ? originalRequestObject.copyObject() : null;
        ErrorObject errorObject1 = errorObject != null ? errorObject.copyObject() : null;

        ResponseObject responseObject = new ResponseObject();
        responseObject.setOriginalRequestObject(requestObject);
        responseObject.setErrorObject(errorObject1);
        responseObject.setJsonObject(jsonObject);
        responseObject.setCode(code);

        return responseObject;
    }

    public int getCode() {
        return code;
    }

    public ErrorObject getErrorObject() {
        return errorObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public RequestObject getOriginalRequestObject() {
        return originalRequestObject;
    }

    public void setCode(int i)
            throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException(CODE_RANGE_EXCP);
        }
        if (i > 1) {
            throw new IllegalArgumentException(CODE_RANGE_EXCP);
        } else {
            code = i;
            return;
        }
    }

    public void setErrorObject(ErrorObject errorobject) {
        errorObject = errorobject;
    }

    public void setJsonObject(JSONObject jsonobject) {
        jsonObject = jsonobject;
    }

    public void setOriginalRequestObject(RequestObject requestobject) {
        originalRequestObject = requestobject;
    }

    @Override
    public String toString() {
        return (new StringBuilder())
                .append("ResponseObject{originalRequestObject=").append(originalRequestObject)
                .append(", errorObject=").append(errorObject)
                .append(", code=").append(code).append(", CODE_RANGE_EXCP='").append("NOT IMPLEMENTING NOW").append('\'')
                .append('}')
                .toString();
    }

    public static final Creator CREATOR = new Creator() {

        public ResponseObject createFromParcel(Parcel parcel) {
            return new ResponseObject(parcel);
        }

        public ResponseObject[] newArray(int i) {
            return new ResponseObject[i];
        }

    };

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(originalRequestObject, i);
        String s;
        if (jsonObject == null) {
            s = "";
        } else {
            s = jsonObject.toString();
        }
        parcel.writeString(s);
        parcel.writeParcelable(errorObject, i);
        parcel.writeInt(code);
    }

}

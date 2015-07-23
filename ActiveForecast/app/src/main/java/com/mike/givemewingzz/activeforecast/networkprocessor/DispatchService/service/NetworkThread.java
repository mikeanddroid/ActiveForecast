package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.os.Bundle;
import android.util.Log;

import com.mike.givemewingzz.activeforecast.UI.CoreApplication;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.exceptions.ConnectionException;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.exceptions.NetworkServiceException;
import com.mike.givemewingzz.activeforecast.utils.ApplicationUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class NetworkThread extends Thread {

    public static final String TAG = NetworkThread.class.getSimpleName();
    /**
     * Network timeout constants
     */
    public static final int STANDARD_CON_TIMEOUT = 10 * 1000;
    public static final int STANDARD_NET_TIMEOUT = 2 * STANDARD_CON_TIMEOUT;
    public static final int OVERALL_TIMEOUT = 15 * 1000;
    /**
     * Media type for Json Requests
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Unique identifer for this request.
     */
    private String identifier = null;
    /**
     * Service that calls this thread.
     */
    private NetworkService parentService = null;
    /**
     * Json request object for post put method calls.
     */
    private JSONObject requestJson;
    /**
     * Network client instance.
     */
    private OkHttpClient client;
    /**
     * Request to be handled by this thread.
     */
    private RequestObject requestObject;
    /**
     * Response as output from this thread.
     */
    ResponseObject responseObject;
    /**
     * Cancel flag to initialze a cancel action. Use in tandem with <code>cancelClient()</code>;
     */
    private boolean initalizeCancel = false;
    /**
     * Header for request.
     */
    Headers.Builder headers = null;

    public NetworkThread(NetworkService networkservice, String threadName, RequestObject requestobject) throws ConnectionException, NetworkServiceException {
        super(threadName);
        Log.d(TAG, "Inside On Network Thread.");
        try {

            if (ApplicationUtils.isNetworkConnected(CoreApplication.getInstance().getBaseContext()) == false) {
                throw new ConnectionException("Data connection Unavailable");
            }

            identifier = threadName;
            parentService = networkservice;
            client = new OkHttpClient();
            client.setConnectTimeout(STANDARD_CON_TIMEOUT, TimeUnit.MILLISECONDS);
            client.setReadTimeout(STANDARD_CON_TIMEOUT, TimeUnit.MILLISECONDS);
            this.requestObject = requestobject;
            requestJson = new JSONObject();
            responseObject = new ResponseObject(requestobject);

            prepareHeader();

            return;
        } catch (ConnectionException connectionException) {
            throw new ConnectionException(connectionException);
        } catch (Exception exception) {
            Log.e(TAG, "Error in the Response : ", exception);
            throw new NetworkServiceException(exception);
        }
    }

    /**
     * Prepare header parameters for request.
     */
    private void prepareHeader() {

        Bundle headerBundle = requestObject.getAuthBundle();
        if (headerBundle != null && !headerBundle.isEmpty()) {
            headers = new Headers.Builder();
            Set<String> keySet = headerBundle.keySet();

            for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
                String key = it.next();
                if (key != null) {
                    String value = (String) headerBundle.get(key);
                    headers.add(key, value);
                }
            }

        }
    }

    @Override
    public void run() {
        final int type = requestObject.getREQUEST_TYPE();

        Log.i(TAG, (new StringBuilder()).append("Run: ").append(requestObject.getBASE_URL()).toString());

        try {

            switch (type) {

                case RequestObject.METHOD_GET:
                    makeGetRequest(requestObject);
                    break;

                default:
                    Log.e(TAG, "Incorrect method type: " + type);

            }

        } catch (SocketTimeoutException e) {
            ErrorObject errObj = new ErrorObject(0);
            errObj.setLogMessage("SocketTimeoutException  " + requestObject);
            responseObject.setCode(ResponseObject.FAILURE);
            responseObject.setErrorObject(errObj);
            parentService.handleNetworkResponse(responseObject);
        } catch (Exception e) {
            ErrorObject errObj = new ErrorObject(0);
            errObj.setLogMessage("Exception  " + requestObject);
            responseObject.setCode(ResponseObject.FAILURE);
            responseObject.setErrorObject(errObj);
            parentService.handleNetworkResponse(responseObject);
            Log.e(TAG, "Run: Exception ", e);
        } finally {
        }

    }

    /**
     * Standard GET request call; also supports DELETE  based on the method type of the Request Object.
     *
     * @param requestobject
     * @throws Exception
     */
    private void makeGetRequest(RequestObject requestobject) throws Exception {

        Response response = null;

        Log.d(TAG, "Inside Make Get Request");
        Bundle requestBundle = requestobject.getRequestBundle();

        Log.d(TAG, (new StringBuilder()).append("Request Bundle : ").append(requestBundle).toString());

        String url = requestobject.getBASE_URL();

        if (requestBundle != null && !requestBundle.isEmpty()) {
            url += prepareEncodedQueryString(requestBundle);
            Log.i(TAG, "Request URL : " + url);
        }

        Request request = null;

        if (headers == null) {
            switch (requestObject.getREQUEST_TYPE()) {
                case RequestObject.METHOD_GET:
                    request = new Request.Builder().url(url).get().build();
                    break;
                case RequestObject.METHOD_DELETE:
                    request = new Request.Builder().url(url).delete().build();
                    break;
            }
        } else {
            switch (requestObject.getREQUEST_TYPE()) {
                case RequestObject.METHOD_GET:
                    request = new Request.Builder().url(url).headers(headers.build()).get().build();
                    break;
                case RequestObject.METHOD_DELETE:
                    request = new Request.Builder().url(url).headers(headers.build()).delete().build();
                    break;
            }
        }

        Call currentOKHttpCall = null;
        Timer timer = setGeneralCancel(currentOKHttpCall = client.newCall(request));
        response = currentOKHttpCall.execute();
        timer.cancel();

        if (response != null) {
            ErrorObject errObj = new ErrorObject(0);
            responseObject.setCode(response.code() == 200 ? ResponseObject.SUCCESS : ResponseObject.FAILURE);
            if (response.code() != 200) {
                errObj.setErrorCode(response.code());
                responseObject.setErrorObject(errObj);
            }
            Log.i(TAG, "makeGetRequest  Response Code " + responseObject.getCode());
            String responseString = response.body().string();

            if (responseString != null) {
                try {
                    responseObject.setJsonObject(new JSONObject(responseString));
                } catch (JSONException exception) {
                    Log.e(TAG, "Json Exception " + exception);
                    responseObject.setJsonObject(new JSONObject());
                } catch (Exception exception) {
                    Log.e(TAG, "Exception " + exception);
                    responseObject.setJsonObject(new JSONObject());
                }
            }
            //persist data with unique identifier
        } else {
            ErrorObject errObj = new ErrorObject(0);
            errObj.setLogMessage("Response is Null  " + requestObject);
            responseObject.setErrorObject(errObj);
        }
        if (initalizeCancel)
            return;
        parentService.handleNetworkResponse(responseObject);

    }

    /**
     * Stamdard put request.
     *
     * @param requestObject
     * @throws Exception
     */
    private void makePutRequest(RequestObject requestObject) throws Exception {
        Log.i(TAG, "makePostRequest  makePutRequest ");
        makePostRequest(requestObject);
    }

    /**
     * Standard POST request; also supports PUT based on the method type of the Request Object.
     *
     * @param requestObject
     * @throws Exception
     */
    private void makePostRequest(RequestObject requestObject) throws Exception {

        Bundle requestBundle = requestObject.getRequestBundle();
        Response response = null;

        if (requestBundle != null && !requestBundle.isEmpty()) {
            prepareJSONRequest(requestBundle);
        }

        RequestBody body = RequestBody.create(JSON, requestJson.toString());

        Request request = null;
        if (headers == null) {
            switch (requestObject.getDATA_TYPE_ID()) {
                case RequestObject.METHOD_POST:
                    request = new Request.Builder().url(requestObject.getBASE_URL()).post(body).build();
                    break;
                case RequestObject.METHOD_PUT:
                    request = new Request.Builder().url(requestObject.getBASE_URL()).put(body).build();
                    break;
            }
        } else {
            switch (requestObject.getDATA_TYPE_ID()) {
                case RequestObject.METHOD_POST:
                    request = new Request.Builder().url(requestObject.getBASE_URL()).headers(headers.build()).post(body).build();
                    break;
                case RequestObject.METHOD_PUT:
                    request = new Request.Builder().url(requestObject.getBASE_URL()).headers(headers.build()).put(body).build();
                    break;
            }
        }


        Call currentOKHttpCall = null;
        Timer timer = setGeneralCancel(currentOKHttpCall = client.newCall(request));
        response = currentOKHttpCall.execute();
        timer.cancel();

        if (response != null) {
            ErrorObject errObj = new ErrorObject(0);
            responseObject.setCode(response.code() == 200 ? ResponseObject.SUCCESS : ResponseObject.FAILURE);
            if (response.code() != 200) {
                errObj.setErrorCode(response.code());
                responseObject.setErrorObject(errObj);
            }

            Log.i(TAG, "makePostRequest  Response Code " + responseObject.getCode());
            String responseString = response.body().string();
            if (responseString != null) {
                try {
                    responseObject.setJsonObject(new JSONObject(responseString));
                } catch (JSONException exception) {
                    Log.e(TAG, "Json Exception " + exception);
                    responseObject.setJsonObject(new JSONObject());
                } catch (Exception exception) {
                    Log.e(TAG, "Exception " + exception);
                    responseObject.setJsonObject(new JSONObject());
                }
            }
            //persist data with unique identifier
        } else {
            ErrorObject errObj = new ErrorObject(0);
            errObj.setLogMessage("Response is Null  " + requestObject);
            responseObject.setErrorObject(errObj);
        }
        if (initalizeCancel)
            return;
        parentService.handleNetworkResponse(responseObject);

    }

    /**
     * Standard DELETE request call.
     *
     * @param requestObject
     * @throws Exception
     */
    private void makeDeleteRequest(RequestObject requestObject) throws Exception {
        Log.i(TAG, "makePostRequest  makeDeleteRequest ");
        makeGetRequest(requestObject);
    }

    /**
     * Prepares a JSON object from bundled kev-value pairs and adds the result to the top level request object. The
     * method will recursively go through bundles to build the JSONObject. Bundles within bundles represents arrays
     * within JSON objects and JSONArray object whose values are without keys are also supported (JSONArray{key:[val1,
     * val2, val3]}) Supports embedded bundles with key value pairs using the constant ApplicationConstants.BUNDLE_OBJECT_NUM.
     * Supports embedded bundles that transpose to arrays of values only using the key
     * ApplicationConstants.BUNDLE_OBJECT_KEYS_ONLY.
     */
    private void prepareJSONRequest(Bundle bundleData) {
        Set<String> keySet = bundleData.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String key = it.next();
            Object value = bundleData.get(key);

            try {
                if (value instanceof Bundle) {
                    Bundle local = (Bundle) value;
                    //key events, value arrabundle
                    parseBundle(key, local);
                } else {
                    requestJson.put(key, value);
                }
                Log.e("NetworkProcessThread", "prepareJSONRequest (Bundle bundleData) \n " + requestJson);
            } catch (JSONException je) {
                Log.d("json exc....", je.toString());
            } catch (Exception e) {
                Log.d("exc in prepare JSON....", e.toString());
            }
        }
    }

    /**
     * Prepares a JSON object from bundled kev-value pairs and adds the result to the top level request object.
     *
     * @param key    key to be assigned to the value determined from processing.
     * @param bundle the bundle holding a single level or multi level (denoted by constants and Bundles within Bundles.
     * @throws JSONException
     */
    private void parseBundle(String key, Bundle bundle) throws JSONException {
        int number = bundle.getInt(ApplicationUtils.ApplicationConstants.BUNDLE_OBJECT_NUM, -1);
        if (number != -1) {
            JSONArray array = new JSONArray();
            for (int k = 0; k < number; k++) {
                Bundle singleJsonBundle = bundle.getBundle(k + "");
                JSONObject obj = getArrayFromBundle(singleJsonBundle);
                if (obj != null) {
                    array.put(obj);
                }
            }
            if (array != null) {
                requestJson.put(key, array);
            }
        } else {
            number = bundle.getInt(ApplicationUtils.ApplicationConstants.BUNDLE_OBJECT_KEYS_ONLY, -1);
            if (number != -1) {
                JSONArray array = new JSONArray();
                array = parseJSONKeysOnlyArrayBundle(bundle);
                if (array != null) {
                    requestJson.put(key, array);
                }
            }
        }
    }

    /**
     * Returns a JSONArray based on the preconfigured structure of the Bundle data.
     *
     * @param bundle
     * @return
     */
    private JSONObject getArrayFromBundle(Bundle bundle) {
        JSONObject object = new JSONObject();
        Set<String> keySet = bundle.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            try {
                String key = it.next();
                Object value = bundle.get(key);
                if (value instanceof Bundle) {
                    Bundle tempBundle = (Bundle) value;
                    if (tempBundle.containsKey(ApplicationUtils.ApplicationConstants.BUNDLE_OBJECT_NUM)) {
                        Object innerObjcet = parseJSONArrayBundle(tempBundle);
                        object.put(key, innerObjcet);
                    } else if (tempBundle.containsKey(ApplicationUtils.ApplicationConstants.BUNDLE_OBJECT_KEYS_ONLY)) {
                        JSONArray innerObjcet = parseJSONKeysOnlyArrayBundle(tempBundle);
                        object.put(key, innerObjcet);
                    }
                } else {
                    object.put(key, value);
                }
                //if value instance of bundle
                //if(value contains BundleOBjectNum
                //use variation of parseBundle above that returns the arrayObject
                //else if not recurse this method
            } catch (JSONException jSONException) {
            }
        }
        return object;
    }

    /**
     * Returns a JSONArray based on the preconfigured structure of the Bundle data.
     *
     * @param bundle
     * @return
     * @throws JSONException
     */
    private JSONArray parseJSONKeysOnlyArrayBundle(Bundle bundle) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        int number = bundle.getInt(ApplicationUtils.ApplicationConstants.BUNDLE_OBJECT_KEYS_ONLY, -1);
        if (number == -1) {
            return jSONArray;
        } else {
            bundle.remove(ApplicationUtils.ApplicationConstants.BUNDLE_OBJECT_KEYS_ONLY);
        }
        Set<String> keySet = bundle.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String key = it.next();
            Object value = bundle.get(key);
            if (value instanceof Bundle) {
                JSONObject obj = getArrayFromBundle((Bundle) value);
                jSONArray.put(obj);
            } else {
                jSONArray.put(bundle.get(key));
            }
        }

        return jSONArray;
    }

    /**
     * Returns a JSONArray based on the preconfigured structure of the Bundle data.
     *
     * @param local
     * @return
     * @throws JSONException
     */
    private JSONArray parseJSONArrayBundle(Bundle local) throws JSONException {
        int number = local.getInt(ApplicationUtils.ApplicationConstants.BUNDLE_OBJECT_NUM);
        JSONArray array = new JSONArray();
        for (int k = 0; k < number; k++) {
            Bundle singleJsonBundle = local.getBundle(k + "");
            JSONObject obj = getArrayFromBundle(singleJsonBundle);
            if (obj != null) {
                array.put(obj);
            }
        }
        return array;
    }

    public static String prepareEncodedQueryString(Bundle bundle) {
        Log.i(TAG, "Inside prepareEncodedQueryString");
        StringBuilder stringbuilder = new StringBuilder("");
        for (Iterator iterator = bundle.keySet().iterator(); iterator.hasNext(); ) {
            String s = (String) iterator.next();
            Object obj = bundle.get(s);
            try {
                stringbuilder.append(s).append("=").append(URLEncoder.encode((new StringBuilder()).append(obj).append("").toString(), "UTF-8")).append("&");
            } catch (UnsupportedEncodingException unsupportedencodingexception) {
                Log.e(TAG, "prepareEncodedQueryString :: UnsupportedEncodingException", unsupportedencodingexception);
            }
        }

        if (stringbuilder.length() > 0) {
            stringbuilder.insert(0, "?");
            stringbuilder.deleteCharAt(stringbuilder.length() - 1);
        }
        return stringbuilder.toString();
    }

    /**
     * Accessor to assign intiate a cancel on the request for this object.
     *
     * @param initalizeCancel
     */
    public void setInitalizeCancel(boolean initalizeCancel) {
        this.initalizeCancel = initalizeCancel;
    }

    /**
     * Convenience method to cancel the OkHttp client by calling client.cancel().
     *
     * @param identifier
     * @throws Exception
     */
    public void cancelClient(String identifier) throws Exception {
        if (client != null && identifier != null)
            if (identifier.equalsIgnoreCase(identifier)) {
                setInitalizeCancel(true);
                client.cancel(identifier);
            }
    }

    /**
     * Cancel timer
     *
     * @param currentOKHttpCall
     * @return
     */
    private Timer setGeneralCancel(final Call currentOKHttpCall) {
        TimerTask timeoutTimer = new TimerTask() {
            @Override
            public void run() {
                if (currentOKHttpCall != null) {
                    currentOKHttpCall.cancel();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(timeoutTimer, OVERALL_TIMEOUT);

        return timer;
    }


}

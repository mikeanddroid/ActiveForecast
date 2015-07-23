package com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.service;

import android.os.Bundle;
import android.util.Log;

import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.exceptions.ConnectionException;
import com.mike.givemewingzz.activeforecast.networkprocessor.DispatchService.exceptions.NetworkServiceException;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.example.givemewingz.activeforecast.mike.services:
//            ResponseObject, RequestObject, ErrorObject, NetworkService

public class NetworkThread extends Thread
{

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

    public NetworkThread(NetworkService networkservice, String s, RequestObject requestobject) throws ConnectionException, NetworkServiceException
    {
        super(s);
        identifier = null;
        parentService = null;
        initalizeCancel = false;
        Log.d(TAG, "Inside On Network Thread.");
        try
        {
            identifier = s;
            parentService = networkservice;
            client = new OkHttpClient();
            client.setConnectTimeout(10000L, TimeUnit.MILLISECONDS);
            client.setReadTimeout(10000L, TimeUnit.MILLISECONDS);
            requestObject = requestobject;
            requestJson = new JSONObject();
            responseObject = new ResponseObject(requestobject);
            return;
        }
        // Misplaced declaration of an exception variable
        catch (NetworkService networkservice)
        {
            Log.e(TAG, "Error in the Response : ", networkservice);
        }
    }

    private void makeGetRequest(RequestObject requestobject)
        throws Exception
    {
        Object obj;
        Object obj1;
        Bundle bundle;
        Log.d(TAG, "Inside Make Get Request");
        bundle = requestobject.getRequestBundle();
        Log.d(TAG, (new StringBuilder()).append("Request Bundle : ").append(bundle).toString());
        obj = requestobject.getBASE_URL();
        obj1 = obj;
        if (bundle != null)
        {
            obj1 = obj;
            if (!bundle.isEmpty())
            {
                obj1 = (new StringBuilder()).append(((String) (obj))).append(prepareEncodedQueryString(bundle)).toString();
                Log.i(TAG, (new StringBuilder()).append("Request URL : ").append(((String) (obj1))).toString());
            }
        }
        bundle = null;
        obj = bundle;
        requestobject.getREQUEST_TYPE();
        JVM INSTR tableswitch 0 3: default 152
    //                   0 309
    //                   1 155
    //                   2 155
    //                   3 330;
           goto _L1 _L2 _L3 _L3 _L4
_L4:
        break MISSING_BLOCK_LABEL_330;
_L3:
        break; /* Loop/switch isn't completed */
_L1:
        obj = bundle;
_L5:
        obj = client.newCall(((com.squareup.okhttp.Request) (obj)));
        obj1 = setGeneralCancel(((Call) (obj)));
        obj = ((Call) (obj)).execute();
        ((Timer) (obj1)).cancel();
        if (obj != null)
        {
            requestobject = new ErrorObject(0);
            obj1 = responseObject;
            int i;
            if (((Response) (obj)).code() == 200)
            {
                i = 1;
            } else
            {
                i = 0;
            }
            ((ResponseObject) (obj1)).setCode(i);
            if (((Response) (obj)).code() != 200)
            {
                requestobject.setErrorCode(((Response) (obj)).code());
                responseObject.setErrorObject(requestobject);
            }
            Log.i(TAG, (new StringBuilder()).append("makeGetRequest  Response Code ").append(responseObject.getCode()).toString());
            requestobject = ((Response) (obj)).body().string();
            if (requestobject != null)
            {
                try
                {
                    responseObject.setJsonObject(new JSONObject(requestobject));
                }
                // Misplaced declaration of an exception variable
                catch (RequestObject requestobject)
                {
                    Log.e(TAG, (new StringBuilder()).append("Json Exception ").append(requestobject).toString());
                    responseObject.setJsonObject(new JSONObject());
                }
                // Misplaced declaration of an exception variable
                catch (RequestObject requestobject)
                {
                    Log.e(TAG, (new StringBuilder()).append("Exception ").append(requestobject).toString());
                    responseObject.setJsonObject(new JSONObject());
                }
            }
        } else
        {
            ErrorObject errorobject = new ErrorObject(0);
            errorobject.setLogMessage((new StringBuilder()).append("Response is Null  ").append(requestobject).toString());
            responseObject.setErrorObject(errorobject);
        }
        if (initalizeCancel)
        {
            return;
        } else
        {
            parentService.handleNetworkResponse(responseObject);
            return;
        }
_L2:
        obj = (new com.squareup.okhttp.Request.Builder()).url(((String) (obj1))).get().build();
          goto _L5
        obj = (new com.squareup.okhttp.Request.Builder()).url(((String) (obj1))).delete().build();
          goto _L5
    }

    public static String prepareEncodedQueryString(Bundle bundle)
    {
        Log.i(TAG, "Inside prepareEncodedQueryString");
        StringBuilder stringbuilder = new StringBuilder("");
        for (Iterator iterator = bundle.keySet().iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            Object obj = bundle.get(s);
            try
            {
                stringbuilder.append(s).append("=").append(URLEncoder.encode((new StringBuilder()).append(obj).append("").toString(), "UTF-8")).append("&");
            }
            catch (UnsupportedEncodingException unsupportedencodingexception)
            {
                Log.e(TAG, "prepareEncodedQueryString :: UnsupportedEncodingException", unsupportedencodingexception);
            }
        }

        if (stringbuilder.length() > 0)
        {
            stringbuilder.insert(0, "?");
            stringbuilder.deleteCharAt(stringbuilder.length() - 1);
        }
        return stringbuilder.toString();
    }

    private Timer setGeneralCancel(final Call currentOKHttpCall)
    {
        currentOKHttpCall = new TimerTask() {

            final NetworkThread this$0;
            final Call val$currentOKHttpCall;

            public void run()
            {
                if (currentOKHttpCall != null)
                {
                    currentOKHttpCall.cancel();
                }
            }


            {
                this$0 = NetworkThread.this;
                currentOKHttpCall = call;
                super();
            }
        };
        Timer timer = new Timer();
        timer.schedule(currentOKHttpCall, 15000L);
        return timer;
    }

    public void run()
    {
        int i = requestObject.getREQUEST_TYPE();
        Log.i(TAG, (new StringBuilder()).append("Run: ").append(requestObject.getBASE_URL()).toString());
        switch (i)
        {
        case 1: // '\001'
        case 2: // '\002'
        default:
            return;

        case 0: // '\0'
            break;
        }
        try
        {
            makeGetRequest(requestObject);
            return;
        }
        catch (SocketTimeoutException sockettimeoutexception)
        {
            ErrorObject errorobject = new ErrorObject(0);
            errorobject.setLogMessage((new StringBuilder()).append("SocketTimeoutException  ").append(requestObject).toString());
            responseObject.setCode(0);
            responseObject.setErrorObject(errorobject);
            parentService.handleNetworkResponse(responseObject);
            return;
        }
        catch (Exception exception)
        {
            ErrorObject errorobject1 = new ErrorObject(0);
            errorobject1.setLogMessage((new StringBuilder()).append("Exception  ").append(requestObject).toString());
            responseObject.setCode(0);
            responseObject.setErrorObject(errorobject1);
            parentService.handleNetworkResponse(responseObject);
            Log.e(TAG, "Run: Exception ", exception);
            return;
        }
    }

}

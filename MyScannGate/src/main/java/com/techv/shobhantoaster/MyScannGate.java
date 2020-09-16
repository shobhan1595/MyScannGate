package com.techv.shobhantoaster;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.techv.shobhantoaster.restapi.Constants;
import com.techv.shobhantoaster.restapi.MyApplication;
import com.techv.shobhantoaster.restapi.ResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public final class MyScannGate {

    public static String licenceKey = null;
    private  static String tenantId;
    private static String sdkLicencekey;
    private static Context context;

    private static MyScannGate myScannGateInstance;


    private MyScannGate(String tenantId, String licenceKey, Context context){
        this.tenantId = tenantId;
        this.sdkLicencekey = licenceKey;
        this.context = context;
    }

    public static synchronized boolean init(String tenantId, String sdkLicenceKey,Context context) {

        if (myScannGateInstance == null) {
            myScannGateInstance = new MyScannGate(tenantId, sdkLicenceKey,context.getApplicationContext());

        }
        return true;
    }

    public static void s(Context c, String message) {

        if (licenceKey == null || licenceKey.isEmpty()) {
            Toast.makeText(c, "licence key should not be empty or not initialized properly", Toast.LENGTH_SHORT).show();
            return;
        }
        if (validateThelicenceKey(licenceKey)) {
            Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "Invalid licence key", Toast.LENGTH_SHORT).show();
        }

    }

    private static boolean validateThelicenceKey(String licenceKey) {

        if (licenceKey.equalsIgnoreCase("shobhan")) {
            return true;
        } else {
            return false;
        }

    }

    public static void verify(JSONObject requestObj, final ResponseHandler responseHandler) throws JSONException {

        if(myScannGateInstance==null){
            Log.d("sdk not initialized","*****");
            responseHandler.onFailure("Sdk not initialized","registration");
            return;
        }

        if(tenantId==null || tenantId.length()<36 || tenantId.length()>36){
            responseHandler.onFailure("Invalid tenantId","registration");
            return;
        }

        if(sdkLicencekey==null || sdkLicencekey.length()<8 || sdkLicencekey.length()>8){
            responseHandler.onFailure("Invalid SDK key","registration");
            return;
        }
        requestObj.put("tenant_id",tenantId);
        requestObj.put("sdk_key",sdkLicencekey);

        if (requestObj == null || requestObj.length() == 0) {
            responseHandler.onFailure("Request object is empty","authentication");
            return;
        } else {

            if (!requestObj.has("entryType")) {
                responseHandler.onFailure("entryType key is missing in the request","authentication");
                return;
            }

            if (!requestObj.has("img")) {
                responseHandler.onFailure("img key is missing in the request","authentication");
                return;
            }


            Log.d("request***", "" + requestObj.toString());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Constants.kFaceVerification_Url, requestObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response***", "" + response);

                            responseHandler.onSuccessCall(response.toString(),"authentication");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error***", "" + error.getMessage());
                    responseHandler.onFailure(error.getMessage().toString(),"authentication");

                }
            });
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000, 5, 1f));
            MyApplication.mInstance.addToRequestQueue(jsonObjReq, Constants.kFaceVerification_Url);
        }

    }


    public static void register(JSONObject requestObj,final ResponseHandler responseHandler) throws JSONException{
        if(myScannGateInstance==null){
            Log.d("sdk not initialized","*****");
            responseHandler.onFailure("Sdk not initialized","registration");
            return;
        }

        if(tenantId==null || tenantId.length()<36 || tenantId.length()>36){
            responseHandler.onFailure("Invalid tenantId","registration");
            return;
        }

        if(sdkLicencekey==null || sdkLicencekey.length()<8 || sdkLicencekey.length()>8){
            responseHandler.onFailure("Invalid SDK key","registration");
            return;
        }
        requestObj.put("tenant_id",tenantId);
        requestObj.put("sdk_key",sdkLicencekey);

        Log.d("request***", "" + requestObj.toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Constants.kRegistration_Url, requestObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response***", "" + response);

                        responseHandler.onSuccessCall(response.toString(),"registration");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error***", "" + error.getMessage());
                responseHandler.onFailure(error.getMessage().toString(),"registration");

            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(60000, 5, 1f));
        MyApplication.mInstance.addToRequestQueue(jsonObjReq, Constants.kRegistration_Url);
    }
}
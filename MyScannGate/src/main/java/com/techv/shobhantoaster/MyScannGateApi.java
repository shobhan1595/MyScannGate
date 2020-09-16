package com.techv.shobhantoaster;

import android.util.Log;

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

public final class MyScannGateApi {

    private MyScannGateApi() {}

    private volatile static MyScannGateApi myScannGateApi = null;

    public static MyScannGateApi getinstance() {
        if(myScannGateApi == null) {
            synchronized (MyScannGateApi.class) {
                myScannGateApi = new MyScannGateApi();

            }
        }
        return myScannGateApi;
    }

    private void verify(JSONObject requestObj, final ResponseHandler responseHandler) throws JSONException {

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

}

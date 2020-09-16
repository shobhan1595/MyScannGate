package com.techv.shobhantoaster.restapi;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MyApplication extends Application {

    String TAG = MyApplication.class.getSimpleName();

    public static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    private int exposer = 0;
    public int MobileOrientation = -1;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public void showToast(String message) {
        if (message!=null) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public void showToast(String message, int length) {
        if (message!=null) {
            message="Unable to connect server";
            Toast.makeText(getApplicationContext(), message, length).show();
        }
    }

    // Creates a default instance of the worker pool

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    // Add request to Queue with specific TAG name

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    // Add request to Queue with default TAG name

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    // Cancel requests

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void setExposer(int val) {
        exposer = val;
    }

    public int getExposer(){
        return exposer;
    }



}

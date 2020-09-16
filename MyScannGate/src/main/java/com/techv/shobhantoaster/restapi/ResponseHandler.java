package com.techv.shobhantoaster.restapi;

public interface ResponseHandler {

    public void onSuccessCall(String response, String fromMethod);

    public void onFailure(String response, String fromMethod);
}
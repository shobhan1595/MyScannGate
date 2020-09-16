package com.techv.shobhantoaster.restapi;

public class Constants {

    /*public static String kRegistration_Url = "http://192.168.2.121:30001/registration";
    public static String kFaceVerification_Url = "http://192.168.2.121:30002/authentication";
*/
    public static String kRegistration_Url = "http://111.93.0.179:8721/registration";
    public static String kFaceVerification_Url = "http://111.93.0.179:8735/authentication";



    //IOT -Platform URls
    public static String techc_iot_base_url = "http://111.93.0.181:9765/api/";

    public static String base_url_techv_iot = techc_iot_base_url+"auth/login";
    public static String create_asset_id = techc_iot_base_url+"asset";
    public static String upload_asset_images = techc_iot_base_url+"uploadEmployeeFrames/";

    public static String registrationWithVA = "http://192.168.3.69:8083/product/register";
    public static String triggerAnEventToVA = "http://192.168.3.69:8083/product/event";


    public static String TOKEN = "token";

}

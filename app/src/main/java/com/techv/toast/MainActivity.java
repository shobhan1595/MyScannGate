package com.techv.toast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.techv.shobhantoaster.MyScannGate;
import com.techv.shobhantoaster.restapi.ResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements ResponseHandler {


    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject jsonObject = new JSONObject();
        JSONObject registObject = new JSONObject();

        pd  = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading....");
        pd.show();


      //  MyScannGate.init("e4596a30-ed16-11ea-97ae-b948fabe1ad2","e4596a30",getApplicationContext());

        try {
            jsonObject.put("entryType","Entry");
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.shobhan);
      /*      Bitmap img1 = BitmapFactory.decodeResource(getResources(), R.drawable.one);
            Bitmap img2 = BitmapFactory.decodeResource(getResources(), R.drawable.two);
            Bitmap img3 = BitmapFactory.decodeResource(getResources(), R.drawable.three);
            Bitmap img4 = BitmapFactory.decodeResource(getResources(), R.drawable.four);
            Bitmap img5 = BitmapFactory.decodeResource(getResources(), R.drawable.five);
            Bitmap img6 = BitmapFactory.decodeResource(getResources(), R.drawable.six);*/
            jsonObject.put("img",encodeImageBase64(icon));
         /*   {'name':"Sreedhar",
                    "employee_id":"TI044",
                    'email':'sreedhar.puleti@techvedika.com',
                    'phone':"8985977528",
                    "last_name":"Puleti",
                    "userType" :"Employee",
                    "tenant_id":"121",
                    "sdk_key":'1Yabc112nchv',
                    "override":True,
                    "image_0":[],
                "image_1":[],
                "image_2":[],
..
            }*/

            registObject.put("name","Shobhan");
            registObject.put("employee_id","T257");
            registObject.put("email","shobhan.nalamasa@techvedika.com");
            registObject.put("phone","9959045349");
            registObject.put("last_name","Nalamasa");
            registObject.put("userType","Employee");

            registObject.put("override","False");
            registObject.put("image_0",encodeImageBase64(icon));
            registObject.put("image_1",encodeImageBase64(icon));
            registObject.put("image_2",encodeImageBase64(icon));
            registObject.put("image_3",encodeImageBase64(icon));
            registObject.put("image_4",encodeImageBase64(icon));
            registObject.put("image_5",encodeImageBase64(icon));
            try {

                //For verification



                MyScannGate.register(registObject,this);

                //For registration
              //  MyScannGate.register(registObject,this);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    String encodeImageBase64(Bitmap bm) {
        if (bm != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] byteText = baos.toByteArray();
            return Base64.encodeToString(byteText, Base64.DEFAULT);
        } else {
            return "";
        }
    }

    @Override
    public void onSuccessCall(String response, String fromMethod) {
        Log.d("onSuccessCall****",""+response);
        if(pd!=null&&pd.isShowing()){
            pd.dismiss();
        }

        try {
            JSONObject responseJson = new JSONObject(response);
            if(responseJson.has("msg")&&responseJson.getString("msg").equalsIgnoreCase("User already exists!")){
                Toast.makeText(MainActivity.this,responseJson.getString("msg"),Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(String response, String fromMethod) {
        Log.d("onFailure****",""+response);
        if(pd!=null&&pd.isShowing()){
            pd.dismiss();
        }
    }
}

package vn.edu.tnut.duycop.asms;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SendCloud {
    String unique, num, msg, time;
    Context context;

    //hàm tạo: contructor
    public SendCloud(String unique, String num, String  msg, String  time, Context context){
        this.unique=unique;
        this.num=num;
        this.msg=msg;
        this.time=time;
        this.context=context;
        Log.d("SMS BACKUP","SendCloud khoi tao doi tuong");
    }
    public boolean postData() {

        JSONObject json = new JSONObject();
        try {
            json.put("unique", unique);
            json.put("num", num);
            json.put("msg", msg);
            json.put("time", time);
        } catch (JSONException e) {
            Log.d("SMS BACKUP","JSONException "+e.getMessage());
            e.printStackTrace();
        }

        String url = "https://duycop.ddns.net/nhan_json.aspx";
        //===================
        JsonObjectRequest postRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SMS BACKUP","response= "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        //===================


        Volley.newRequestQueue(context).add(postRequest);

        return true;
    }

}

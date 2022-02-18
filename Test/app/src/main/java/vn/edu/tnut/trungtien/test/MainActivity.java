package vn.edu.tnut.trungtien.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONArray;


public class MainActivity extends AppCompatActivity{
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.data);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://tms.tnut.edu.vn/api.aspx?action=counter_online";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response);
                        try{
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            textView.setText("Số người online : "+object.getString("counter_online"));
                        }catch (Exception e){
                            textView.setText("Khong lay dc !!");
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }

}
package vn.edu.tnut.trungtien.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Post extends AppCompatActivity
{
    private Button btnSend;
    private EditText editTextMavs;
    private EditText editTextNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnSend = findViewById(R.id.btn_send);
        editTextMavs = findViewById(R.id.etxt_msv);
        editTextNote = findViewById(R.id.etxtNt);

        // nút send
        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String m = editTextMavs.getText().toString();
                String n = editTextNote.getText().toString();
                String url ="http://tms.tnut.edu.vn:9630/54kmt/";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Post.this, response.trim(), Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(Post.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("masv", m);
                        params.put("note", n);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(Post.this);
                requestQueue.add(stringRequest);
            }
        });
    }
}
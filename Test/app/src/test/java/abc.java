package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivitySinhVienOnl extends AppCompatActivity {
    Button btntrangchusv,btnpostdatasv,btdoctruyensv;
    Button btn;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_vien_onl);
        btntrangchusv=findViewById(R.id.btntrangchu);
        btnpostdatasv=findViewById(R.id.btnpostdatasv);
        btdoctruyensv=findViewById(R.id.btndoctruyen);
        txt=findViewById(R.id.textView);
        btdoctruyensv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySinhVienOnl.this,ActivityDocTruyen.class);
                startActivity(intent);
            }
        });
        btntrangchusv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySinhVienOnl.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnpostdatasv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySinhVienOnl.this,ActivityPostData.class);
                startActivity(intent);
            }
        });
        //load sv online

        btn=findViewById(R.id.btnXemsv);
        txt=findViewById(R.id.txtSinhvien);
        OkHttpClient client = new OkHttpClient();
        String url="https://tms.tnut.edu.vn/api.aspx?action=counter_online";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String conten = response.body().string();
                    String a= conten.substring(18);
                    int dodai=a.length();
                    String count =a.substring(0,(dodai-1));
                    ActivitySinhVienOnl.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt.setText("Số sinh viên đang online là : "+count);
                        }
                    });
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn=findViewById(R.id.btnXemsv);
                txt=findViewById(R.id.txtSinhvien);
                OkHttpClient client = new OkHttpClient();
                String url="https://tms.tnut.edu.vn/api.aspx?action=counter_online";
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            String conten = response.body().string();
                            String a= conten.substring(18);
                            int dodai=a.length();
                            String count =a.substring(0,(dodai-1));
                            ActivitySinhVienOnl.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txt.setText("Số sinh viên đang online là : "+count);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
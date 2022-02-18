package com.example.bai_01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // gọi onCreate của lớp bố
        setContentView(R.layout.activity_main); // thiết lập giao diện theo file activity_main.xml


    }
}
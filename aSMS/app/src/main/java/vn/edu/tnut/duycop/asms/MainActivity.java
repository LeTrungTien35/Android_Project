package vn.edu.tnut.duycop.asms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    BackupSMS mySMS=new BackupSMS();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("SMS BACKUP","aSMS MainActivity onCreate");
    }
}
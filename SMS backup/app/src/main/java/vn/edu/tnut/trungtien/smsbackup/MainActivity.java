package vn.edu.tnut.trungtien.smsbackup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private BackupSMS _receiver = new BackupSMS ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("SMS BACKUP","MainActivity version 9h43");
    }
}
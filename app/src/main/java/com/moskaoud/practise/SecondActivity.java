package com.moskaoud.practise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.sa_tv_message);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            Bundle remoteReplay = RemoteInput.getResultsFromIntent(getIntent());
            if(remoteReplay != null )
            {
                String messsage = remoteReplay.getCharSequence(MainActivity.TXT_REPLAY).toString();
                textView.setText(messsage);

            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(MainActivity.NOTIFICATION_ID);
        }
    }
}












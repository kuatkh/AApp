package com.example.aapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MessageListenerInterface {

    private TextView msgTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // initializing variables on below line.
        msgTV = findViewById(R.id.idTVMessage);
        // adding bind listener for message receiver on below line.
        MessageBroadcastReceiver.bindListener(this);
        requestSmsPermission();
    }
    private void requestSmsPermission() {

        // check permission is given
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_SMS, android.Manifest.permission.RECEIVE_SMS},
                    123);
        }
    }

    @Override
    public void messageReceived(String message) {
        // setting message in our text view on below line.
        msgTV.setText(message);
    }
}
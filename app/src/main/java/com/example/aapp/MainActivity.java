package com.example.aapp;

import androidx.appcompat.app.AppCompatActivity;

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
    }

    @Override
    public void messageReceived(String message) {
        // setting message in our text view on below line.
        msgTV.setText(message);
    }
}
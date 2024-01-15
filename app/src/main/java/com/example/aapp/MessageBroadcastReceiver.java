package com.example.aapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MessageBroadcastReceiver extends BroadcastReceiver {
    // creating a variable for a message listener interface on below line.
    private static MessageListenerInterface mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            for (SmsMessage smsMessage : smsMessages) {
                // Do whatever you want to do with SMS.
                String msg = smsMessage.getMessageBody();
                String code = "";

                final Pattern p = Pattern.compile( "(\\d{4})" );
                final Matcher m = p.matcher(msg);
                if ( m.find() ) {
                    code = String.valueOf(m.group());
                } else {
                    code = msg.replaceAll("\\D+","");
                }

                if (code.length() >= 4) {
                    String iin = "123456123456";

                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url = "http://78.140.245.88/api/Request?iin=" + iin + "&code=" + code;

                    if (code.length() == 7) {
                        url = url + "&msg=hellooo";
                    }

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    mListener.messageReceived(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mListener.messageReceived("Во время подтверждения произошла ошибка: " + error.toString());
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

                // adding the message to listener on below line.
                mListener.messageReceived("Код: " + code);
            }
        }
        /*
        // getting bundle data on below line from intent.
        Bundle data = intent.getExtras();
        // creating an object on below line.
        Object[] pdus = (Object[]) data.get("pds");
        // running for loop to read the sms on below line.
        for (int i = 0; i < pdus.length; i++) {
            // getting sms message on below line.
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            // extracting the sms from sms message and setting it to string on below line.
            String message = "Sender : " + smsMessage.getDisplayOriginatingAddress()
                    + "Message: " + smsMessage.getMessageBody();
            // adding the message to listener on below line.
            mListener.messageReceived(message);
        }*/
    }

    // on below line we are binding the listener.
    public static void bindListener(MessageListenerInterface listener) {
        mListener = listener;
    }
}

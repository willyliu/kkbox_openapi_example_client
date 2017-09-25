package com.kkbox.openapiclient;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.kkbox.openapi.ClientInfo;
import com.kkbox.openapi.TokenFetcher;
import com.koushikdutta.ion.future.ResponseFuture;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context appContext = getApplicationContext();
        TokenFetcher fetcher = new TokenFetcher(ClientInfo.CLIENT_ID, ClientInfo.CLIENT_SECRET, appContext);
        ResponseFuture<JsonObject> responseFurure = fetcher.fetchAccessTokenWithClientCredentials();
        try {
            JsonObject response = responseFurure.get();
            String token = response.get("access_token").getAsString();
            TextView responseTextView = findViewById(R.id.responseTextView);
            responseTextView.setText(token);
        }
        catch (Exception e) {
            Log.d("error", e.toString());
        }
    }
}

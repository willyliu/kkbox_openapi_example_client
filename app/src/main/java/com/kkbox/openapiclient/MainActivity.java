package com.kkbox.openapiclient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsic;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kkbox.openapi.ClientInfo;
import com.kkbox.openapi.HttpClient;
import com.kkbox.openapi.SearchFetcher;
import com.kkbox.openapi.TokenFetcher;
import com.kkbox.openapiclient.TrackInfo;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get token
        Context appContext = getApplicationContext();
        TokenFetcher fetcher = new TokenFetcher(ClientInfo.CLIENT_ID, ClientInfo.CLIENT_SECRET, appContext);
        ResponseFuture<JsonObject> responseFurure = fetcher.fetchAccessTokenWithClientCredentials();
        try {
            JsonObject response = responseFurure.get();
            String token = response.get("access_token").getAsString();
            HttpClient client = new HttpClient(token, appContext);

            //set search view listener
            final SearchView searchView = findViewById(R.id.search_bar);
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String q) {
                    generateData(q);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

            final ListView listView = findViewById(R.id.listView);


//            // get search result
//            SearchFetcher searchFetcher = new SearchFetcher(client, "TW");
//            ResponseFuture<JsonObject> searchResult = searchFetcher.setSearchCriteria("love", "track").fetchSearchResult(15, 0);
//
//            // parse search result
//            JsonObject searchResponse = searchResult.get();
//            JsonObject tracks = searchResponse.get("tracks").getAsJsonObject();
//            JsonArray data = tracks.get("data").getAsJsonArray();
//            StringBuilder songNames = new StringBuilder();
//            for (int i = 0; i < data.size(); i++) {
//                JsonObject track = data.get(i).getAsJsonObject();
//                JsonPrimitive namePrimitive = track.getAsJsonPrimitive("name");
//                String name = namePrimitive.getAsString();
//                String line = String.format(Locale.getDefault(), "%d. %s\n", i + 1, name);
//                songNames.append(line);
//            }
//
//            // displays all songs' names
//            TextView responseTextView = findViewById(R.id.responseTextView);
//            responseTextView.setText(songNames.toString());
        }
        catch (Exception e) {
            Log.d("error", e.toString());
        }
    }

    private final void generateData(final String q) {
        loading = true;
        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {
                final ArrayList<TrackInfo> result = new ArrayList<>();

            }
        });
    }
}

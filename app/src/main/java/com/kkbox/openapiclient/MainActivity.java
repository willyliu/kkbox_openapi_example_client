package com.kkbox.openapiclient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kkbox.openapi.ClientInfo;
import com.kkbox.openapi.HttpClient;
import com.kkbox.openapi.SearchFetcher;
import com.kkbox.openapi.TokenFetcher;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    boolean loading;
    boolean hasMore;
    HttpClient client;
    SearchFetcher searchFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get token
        Context appContext = getApplicationContext();
        TokenFetcher fetcher = new TokenFetcher(ClientInfo.CLIENT_ID, ClientInfo.CLIENT_SECRET, appContext);
        ResponseFuture<JsonObject> responseFuture = fetcher.fetchAccessTokenWithClientCredentials();
        try {
            JsonObject response = responseFuture.get();
            String token = response.get("access_token").getAsString();
            client = new HttpClient(token, appContext);

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
        }
        catch (Exception e) {
            Log.d("error", e.toString());
        }

        final ListView listView = findViewById(R.id.listView);
        TrackListAdapter adapter = new TrackListAdapter(this);
        if(listView != null) {
            listView.setAdapter(adapter);
        }
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView var1, int var2) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0){
                    if (!loading) {
                        fetchMoreData();
                    }
                }
            }
        });
    }

    private void generateData(final String q) {
        loading = true;
        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {

                final ArrayList<TrackInfo> result = new ArrayList<>();
                searchFetcher = new SearchFetcher(client, "TW");

                try {
                    JsonObject searchResult = searchFetcher.setSearchCriteria(q, "track").fetchSearchResult(15, 0).get();
                    parseSearchResult(searchResult, result);

                    JsonObject response = searchResult.getAsJsonObject();
                    JsonObject track = response.getAsJsonObject("tracks");
                    if (track != null && !track.isJsonNull()) {
                        JsonObject paging = track.get("paging").getAsJsonObject();
                        JsonElement nextUri = paging.get("next");

                        hasMore = (nextUri != null)? !nextUri.isJsonNull() : false;
                    }

                    MainActivity.this.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            final ListView listView = MainActivity.this.findViewById(R.id.listView);
                            TrackListAdapter trackListAdapter = (TrackListAdapter)listView.getAdapter();
                            trackListAdapter.items = result;
                            trackListAdapter.notifyDataSetChanged();
                            loading = false;
                        }
                    });
                }
                catch (Exception e) {
                    Log.d("error", e.toString());
                }
            }
        });
    }

    private void fetchMoreData() {
        if (!hasMore) {
            return;
        }
        loading = true;
        final ListView listView = MainActivity.this.findViewById(R.id.listView);
        final TrackListAdapter trackListAdapter = (TrackListAdapter)listView.getAdapter();
        final int offset = trackListAdapter.items.size();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final ArrayList<TrackInfo> result = new ArrayList<>();

                try {
                    JsonObject searchResult = searchFetcher.fetchSearchResult(15, offset).get();
                    parseSearchResult(searchResult, result);

                    JsonObject response = searchResult.getAsJsonObject();
                    JsonObject track = response.get("tracks").getAsJsonObject();
                    if (track != null && !track.isJsonNull()) {
                        JsonObject paging = track.get("paging").getAsJsonObject();
                        JsonElement nextUri = paging.get("next");

                        hasMore = (nextUri != null)? !nextUri.isJsonNull() : false;
                    }

                    MainActivity.this.runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            trackListAdapter.items.addAll(result);
                            trackListAdapter.notifyDataSetChanged();
                            loading = false;
                        }
                    });
                }
                catch (Exception e) {
                    Log.d("error", e.toString());
                }

            }
        });

    }

    private void parseSearchResult(JsonObject searchResult, ArrayList<TrackInfo> result) {
        if (searchResult == null) {
            return;
        }
        if (searchResult.isJsonNull()) {
            return;
        }
        try {
            JsonObject searchResponse = searchResult;
            JsonObject tracks = searchResponse.getAsJsonObject("tracks");
            if (tracks == null || tracks.isJsonNull()) {
                return;
            }
            JsonArray data = tracks.get("data").getAsJsonArray();
            for (int i = 0; i < data.size(); i++) {
                JsonObject track = data.get(i).getAsJsonObject();
                JsonPrimitive trackPrimitive = track.getAsJsonPrimitive("name");
                String trackName = trackPrimitive.getAsString();

                JsonObject album = track.getAsJsonObject("album");
                JsonObject images = album.get("images").getAsJsonArray().get(0).getAsJsonObject();
                JsonPrimitive albumPrimitive = images.getAsJsonPrimitive("url");
                String albumImage = albumPrimitive.getAsString();

                JsonObject artist = album.getAsJsonObject("artist");
                JsonPrimitive artistPrimitive = artist.getAsJsonPrimitive("name");
                String artistName = artistPrimitive.getAsString();

                TrackInfo trackInfo = new TrackInfo(trackName, artistName, albumImage);
                result.add(trackInfo);
            }
        }
        catch (Exception e) {
            Log.d("error", e.toString());
        }
    }
}

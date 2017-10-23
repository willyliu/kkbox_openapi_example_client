package com.kkbox.openapiclient;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private static final int PAGE_SIZE = 15;

    private MainContract.View view;

    private OpenApiWrapper openApi;

    private String keyword;

    private List<TrackInfo> tracks = new ArrayList<>();

    private boolean loading;

    private boolean hasMore;

    public MainPresenter(MainContract.View view, OpenApiWrapper openApi) {
        this.view = view;
        this.openApi = openApi;
    }

    @Override
    public void search(String keyword) {
        this.keyword = keyword;
        searchImpl(0);
    }

    @Override
    public void loadMore() {
        if (!hasMore) {
            return;
        }

        searchImpl(tracks.size());
    }

    private void searchImpl(final int offset) {
        if (loading) {
            return;
        }

        loading = true;

        new AsyncTask<Void, Void, List<TrackInfo>>() {
            @Override
            protected List<TrackInfo> doInBackground(Void... params) {
                ArrayList<TrackInfo> tracks = new ArrayList<>();

                try {
                    JsonObject response = openApi.searchTracks(keyword, PAGE_SIZE, offset);
                    hasMore = parseSearchResult(response, tracks);
                    return tracks;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void onPostExecute(List<TrackInfo> tracks) {
                MainPresenter.this.keyword = keyword;

                if (offset == 0) {
                    MainPresenter.this.tracks = tracks;
                    view.showTracks(tracks);
                } else {
                    MainPresenter.this.tracks.addAll(tracks);
                    view.showMoreTracks(tracks);
                }

                loading = false;
            }
        }.execute();
    }

    private boolean parseSearchResult(JsonObject searchResult, ArrayList<TrackInfo> result) {
        if (searchResult == null) {
            return false;
        }
        if (searchResult.isJsonNull()) {
            return false;
        }
        try {
            JsonObject searchResponse = searchResult;
            JsonObject tracks = searchResponse.getAsJsonObject("tracks");
            if (tracks == null || tracks.isJsonNull()) {
                return false;
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

            // hasMore
            JsonObject paging = tracks.get("paging").getAsJsonObject();
            JsonElement nextUri = paging.get("next");

            return (nextUri != null)? !nextUri.isJsonNull() : false;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

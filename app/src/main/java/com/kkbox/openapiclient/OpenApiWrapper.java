package com.kkbox.openapiclient;

import android.content.Context;

import com.google.gson.JsonObject;
import com.kkbox.openapi.HttpClient;
import com.kkbox.openapi.SearchFetcher;
import com.kkbox.openapi.TokenFetcher;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.concurrent.ExecutionException;

public class OpenApiWrapper {

    private static final String CLIENT_ID = "99900ec8c16790e5df8ca4b0570ac2ae";

    private static final String CLIENT_SECRET = "51e4a4b2c47f448a3644c9215724d100";

    private Context context;

    private SearchFetcher searchFetcher;

    public OpenApiWrapper(Context context) {
        this.context = context;
    }

    public OpenApiWrapper init() {
        HttpClient client = createHttpClient();
        searchFetcher = new SearchFetcher(client, "TW");

        return this;
    }

    private HttpClient createHttpClient() {
        TokenFetcher fetcher = new TokenFetcher(CLIENT_ID, CLIENT_SECRET, context);
        ResponseFuture<JsonObject> responseFuture = fetcher.fetchAccessTokenWithClientCredentials();

        try {
            JsonObject response = responseFuture.get();
            String token = response.get("access_token").getAsString();

            return new HttpClient(token, context);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonObject searchTracks(String keyword, int limit, int offset) throws InterruptedException, ExecutionException {
        return searchFetcher.setSearchCriteria(keyword, "track").fetchSearchResult(limit, offset).get();
    }

}

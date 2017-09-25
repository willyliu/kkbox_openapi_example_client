package com.kkbox.openapiclient;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonObject;
import com.kkbox.openapi.ClientInfo;
import com.kkbox.openapi.HttpClient;
import com.kkbox.openapi.SearchFetcher;
import com.kkbox.openapi.TokenFetcher;
import com.koushikdutta.ion.future.ResponseFuture;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TokenFetcherTest {
    private String token = null;

    @Before
    public void fetchToken() throws InterruptedException, ExecutionException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TokenFetcher fetcher = new TokenFetcher(ClientInfo.CLIENT_ID, ClientInfo.CLIENT_SECRET, appContext);
        ResponseFuture<JsonObject> responseFurure = fetcher.fetchAccessTokenWithClientCredentials();
        JsonObject response = responseFurure.get();
        String fetchedToken = response.get("access_token").getAsString();
        assertTrue(fetchedToken.length() > 0);
        this.token = fetchedToken;
    }

    @Test
    public void testSearch() throws InterruptedException, ExecutionException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpClient client = new HttpClient(token, appContext);
        SearchFetcher fetcher = new SearchFetcher(client, "TW");
        ResponseFuture<JsonObject> response = fetcher.setSearchCriteria("love", "track").fetchSearchResult(15, 0);
        JsonObject searchResponse = response.get();
        String tracksResult = searchResponse.get("tracks").toString();
        assertTrue(tracksResult .length() > 0);
    }
}

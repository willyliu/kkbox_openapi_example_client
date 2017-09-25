package com.kkbox.openapiclient;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonObject;
import com.kkbox.openapi.ClientInfo;
import com.kkbox.openapi.TokenFetcher;
import com.koushikdutta.ion.future.ResponseFuture;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TokenFetcherTest {
    @Test
    public void testFetchTokenWithClientCredentials() throws InterruptedException, ExecutionException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        TokenFetcher fetcher = new TokenFetcher(ClientInfo.CLIENT_ID, ClientInfo.CLIENT_SECRET, appContext);
        ResponseFuture<JsonObject> responseFurure = fetcher.fetchAccessTokenWithClientCredentials();
        JsonObject response = responseFurure.get();
        assert(response.get("access_token").getAsString().length() > 0);
    }
}

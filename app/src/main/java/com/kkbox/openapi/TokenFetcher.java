package com.kkbox.openapi;

import android.content.Context;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The instance of this class will fetch access token.
 */
public class TokenFetcher {
    private String clientID;
    private String clientSecret;
    private Context context;

    /**
     * Default constructor
     * @param clientID
     * @param clientSecret
     * @param context The context for http connection
     */
    public TokenFetcher(String clientID, String clientSecret, Context context) {
        super();
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.context = context;
    }

    /**
     * Fetches access token with client credentials.
     */
    public ResponseFuture<JsonObject> fetchAccessTokenWithClientCredentials() {
        HashMap<String, List<String>> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add("client_credentials");
        params.put("grant_type", values);
        return this.fetchAccessToken(params);
    }

    /**
     * Fetches the access token.
     *
     * @return the Future object that will return the token api response.
     */
    private ResponseFuture<JsonObject> fetchAccessToken(Map<String, List<String> > bodyParameters) {
        return Ion.with(context)
                .load("https://account.kkbox.com/oauth2/token")
                .basicAuthentication(clientID, clientSecret)
                .setHeader("User-Agent", "KKBOX Android SDK")
                .setBodyParameters(bodyParameters)
                .asJsonObject();
    }
}

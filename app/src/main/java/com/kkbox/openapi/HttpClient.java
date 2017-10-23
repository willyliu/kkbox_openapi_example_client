package com.kkbox.openapi;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.Map;

/**
 * Handles http connection.
 */
public class HttpClient {
    private String token = null;
    private Context context = null;

    /**
     * Constructs a http client instance.
     * @param token The token for accessing api
     * @param context The context for http connection
     */
    public HttpClient(String token, Context context) {
        this.token = "Bearer " + token;
        this.context = context;
    }

    /**
     * Gets api response.
     * @param endpoint The endpoint of the api
     * @param params The url's get parameters
     * @return The api response
     */
    ResponseFuture<JsonObject> get(String endpoint, Map<String, String> params) {
        Uri.Builder builder = Uri.parse(endpoint).buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.appendQueryParameter(key, value);
        }
        Uri resultUri = builder.build();
        return Ion.with(context)
                .load(resultUri.toString())
                .setHeader("Authorization", token)
                .setHeader("User-Agent", "KKBOX Openapi Android SDK")
                .setLogging("http-client", Log.VERBOSE)
                .asJsonObject();
    }
}

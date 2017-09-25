package com.kkbox.openapi;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.HashMap;
import java.util.Map;

/**
 * Fetches search result.
 */
public class SearchFetcher {
    private String q = null;
    private String type = "track";
    private String territory = "TW";
    private HttpClient httpClient = null;
    private String endpoint = "https://api.kkbox.com/v1.1/search";

    /**
     * Default constructor.
     * @param httpClient The http client for fetching api.
     * @param territory The territory, must be one of 'TW', 'HK', 'SG', 'MY' or 'JP'
     */
    public SearchFetcher(HttpClient httpClient, String territory) {
        this.httpClient = httpClient;
        this.territory = territory;
    }

    /**
     * Set search criteria to specific keyword and type.
     * @param q The keyword to search
     * @param type The type of search.
     *             Must be combination of 'artist', 'album', 'track', or 'playlist'.
     *             If you want to use multiple type
     *             at the same time, you may use "," to separate them.
     * @return The instance of this.
     */
    public SearchFetcher setSearchCriteria(String q, String type) {
        this.q = q;
        this.type = type;
        return this;
    }

    /**
     * Fetches search result. Result will be paged.
     *
     * @param limit The limit of fetching.
     * @param offset The offset of fetching
     * @return The api response.
     */
    public ResponseFuture<JsonObject> fetchSearchResult(int limit, int offset) {
        Map<String, String> params = new HashMap<>();
        if (q != null) {
            params.put("q", q);
        }
        if (territory != null) {
            params.put("territory", territory);
        }
        if (type != null) {
            params.put("type", type);
        }
        if (limit > 0) {
            params.put("limit", Integer.toString(limit));
        }
        if (offset > 0) {
            params.put("offset", Integer.toString(offset));
        }
        return httpClient.get(endpoint, params);
    }
}

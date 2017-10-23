package com.kkbox.openapiclient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.kkbox.openapiclient.MainContract.Presenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class MainPresenterTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private MainContract.View view;

    @Mock
    private OpenApiWrapper openApi;

    private MainContract.Presenter presenter;

    @Before
    public void setUp() {
        presenter = new MainPresenter(view, openApi);
    }

    @Test
    public void search_NoResult_ShowNothing() throws Exception {
        // Arrange (stubbing)
        String keyword = "asdfasdf";
        when(openApi.searchTracks(keyword, 15, 0)).thenReturn(loadJsonObject("no-result.json"));

        // Act
        presenter.search(keyword);

        // Assert
        verify(view).showTracks(Collections.<TrackInfo>emptyList());
    }

    private JsonObject loadJsonObject(String resource) {
        InputStreamReader reader = new InputStreamReader(
                MainPresenterTest.class.getResourceAsStream(resource));

        JsonParser parser = new JsonParser();
        return parser.parse(new JsonReader(reader)).getAsJsonObject();
    }

}

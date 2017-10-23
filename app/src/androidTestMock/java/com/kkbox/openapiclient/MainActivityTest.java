package com.kkbox.openapiclient;

import android.support.test.rule.ActivityTestRule;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.InputStreamReader;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.mockito.Mockito.when;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();

            MockitoAnnotations.initMocks(MainActivityTest.this);
            Injection.mock(OpenApiWrapper.class, openApiWrapper);
            when(openApiWrapper.init()).thenReturn(openApiWrapper);
        }
    };

    @Mock
    private OpenApiWrapper openApiWrapper;

    @Test
    public void search_FewTracks() throws Exception {
        // Arrange
        String KEYWORD = "aaaaaaaa";
        when(openApiWrapper.searchTracks(KEYWORD, 15, 0)).thenReturn(loadJsonObject("2-items.json"));

        // Act
        onView(withId(R.id.search_bar)).perform(typeText(KEYWORD + "\n"));

        // Assert
        onData(anything()).atPosition(1).onChildView(withId(R.id.txtArtist)).check(matches(withText("RiNG-O.TV")));
    }

    private JsonObject loadJsonObject(String resource) {
        InputStreamReader reader = new InputStreamReader(
                MainActivityTest.class.getResourceAsStream(resource));

        JsonParser parser = new JsonParser();
        return parser.parse(new JsonReader(reader)).getAsJsonObject();
    }

}
